package com.bheaver.ngl4.aa.services

import com.bheaver.ngl4.aa.exceptions.AuthenticationException
import com.bheaver.ngl4.aa.model.requests.AuthenticateRequest
import com.bheaver.ngl4.aa.model.responses.{AuthenticateResponse, Patron}
import com.bheaver.ngl4.aa.utils.MD5Utils
import com.bheaver.ngl4.util.mongoUtils.{Database}
import com.bheaver.ngl4.util.constants.MongoCollections._
import com.mongodb.client.model.{Filters => filters}
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.scala.Logging
import reactor.core.publisher.{Flux, Mono}

trait AuthenticateService {
  @throws(classOf[AuthenticationException])
  def authenticateUser(authenticateRequest: AuthenticateRequest): Mono[Either[AuthenticationException, AuthenticateResponse]]
}

class AuthenticateServiceImpl(database: Database,
                              jwtService: JWTService) extends AuthenticateService with Logging {

  @throws(classOf[AuthenticationException])
  override def authenticateUser(authenticateRequest: AuthenticateRequest): Mono[Either[AuthenticationException, AuthenticateResponse]] = {
    val collection = database.getDatabase(authenticateRequest.libCode).getCollection(USER)

    val m1: Mono[Either[AuthenticationException, Patron]] = Mono.from(collection.find(filters.eq("patron_id", authenticateRequest.username)).first()).map(document => {
      println(MD5Utils.md5HashString(authenticateRequest.password))
      if (!document.getString("user_password").equals(MD5Utils.md5HashString(authenticateRequest.password))) {
        val exp = new AuthenticationException("Authentication exception")
        Left(exp)
      } else {
        val fname = document.getString("fname")
        val mname = document.getString("mname")
        val lname = document.getString("lname")
        Right(Patron(document.getString("patron_id"), fname))
      }
    })
    Mono.zip(m1, jwtService.encrypt).map(tuple2 => {
      val eitherPat = tuple2.getT1
      val jwtstr = tuple2.getT2
      eitherPat match {
        case Left(value) => Left(value)
        case Right(value) => Right(AuthenticateResponse(true, jwtstr, value, null))
      }
    }
    )


  }


}