package com.bheaver.ngl4.aa.services

import com.bheaver.ngl4.aa.exceptions.AuthenticationException
import com.bheaver.ngl4.util.mongoUtils.{DBConnectionImpl, DatabaseImpl}
import com.bheaver.ngl4.util.constants.MongoCollections._
import com.mongodb.client.model.{Filters => filters}
import org.bson.Document
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

import scala.util.Try

trait AuthenticateService {
  def authenticateUser(userId: String, password: Array[Char], libraryCode: String): Flux[String]
}

class AuthenticateServiceImpl extends AuthenticateService {
  override def authenticateUser(userId: String, password: Array[Char], libraryCode: String): Flux[String] = {
    val database = DatabaseImpl(DBConnectionImpl apply, libraryCode)
    val collection = database.getCollection(USER)
    Flux.concat(collection.find(filters.eq("patron_id", userId)).first()).map(document => {
      document.getString("patron_id")
    })
  }
}