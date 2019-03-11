package com.bheaver.ngl4.aa.services

import com.bheaver.ngl4.aa.exceptions.AuthenticationException
import com.bheaver.ngl4.util.mongoUtils.{DBConnection, Database, DatabaseImpl}
import com.bheaver.ngl4.util.constants.MongoCollections._
import com.mongodb.client.model.{Filters => filters}
import org.bson.Document
import org.reactivestreams.Publisher
import reactor.core.publisher.{Flux, Mono}

import scala.util.Try

trait AuthenticateService {
  def authenticateUser(userId: String, password: Array[Char], libraryCode: String): Mono[String]
}

class AuthenticateServiceImpl(database: Database) extends AuthenticateService {
  override def authenticateUser(userId: String, password: Array[Char], libraryCode: String): Mono[String] = {
    val collection = database.getDatabase(libraryCode).getCollection(USER)
    Mono.from(collection.find(filters.eq("patron_id", userId)).first()).map(document => {
      document.getString("patron_id")
    })
  }
}