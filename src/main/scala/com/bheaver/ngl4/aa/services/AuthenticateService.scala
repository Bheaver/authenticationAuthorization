package com.bheaver.ngl4.aa.services

import com.bheaver.ngl4.aa.exceptions.AuthenticationException
import com.bheaver.ngl4.aa.model.requests.AuthenticateRequest
import com.bheaver.ngl4.aa.model.responses.{AuthenticateResponse, Patron}
import com.bheaver.ngl4.aa.utils.MD5Utils
import com.bheaver.ngl4.util.mongoUtils.Database
import com.bheaver.ngl4.util.constants.MongoCollections._
import com.mongodb.client.model.{Filters => filters}
import org.apache.logging.log4j.scala.Logging
import reactor.core.publisher.{Mono}


trait AuthenticateService {
  @throws(classOf[AuthenticationException])
  def authenticateUser(authenticateRequest: AuthenticateRequest): Mono[Either[AuthenticationException, AuthenticateResponse]]
}

class AuthenticateServiceImpl(database: Database,
                              jwtService: JWTService) extends AuthenticateService with Logging {

  @throws(classOf[AuthenticationException])
  override def authenticateUser(authenticateRequest: AuthenticateRequest): Mono[Either[AuthenticationException, AuthenticateResponse]] = {
    val collection = database.getDatabase(authenticateRequest.libCode).getCollection(USER)

    Mono.from(collection.find(filters.eq("patron_id", authenticateRequest.username)).first()).map[Option[Patron]](document => {
      if (!document.getString("user_password").equals(MD5Utils.md5HashString(authenticateRequest.password))) {
        None
      } else {
        val fname = document.getString("fname")
        val mname = document.getString("mname")
        val lname = document.getString("lname")
        val patronCategoryId = document.getInteger("patron_category_id").toString

        Some(Patron(document.getString("patron_id"), s"${fname} ${mname} ${lname}", patronCategoryId))
      }
    })
      .zipWhen[String](optionalPatron => {
      optionalPatron match {
        case Some(value) => jwtService.encrypt(value)
        case None => Mono.just("")
      }
    })
      .map(tuple => {
      tuple.getT1 match {
        case Some(value) => Right(new AuthenticateResponse(true,tuple.getT2,value,null))
        case None => Left(new AuthenticationException("Authentication failed"))
      }
    })
  }
}