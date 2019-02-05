package com.bheaver.ngl4.aa.services

import com.bheaver.ngl4.aa.exceptions.AuthenticationException
import com.mongodb.client.MongoClient

import scala.util.Try

trait AuthenticateService {
    def authenticateUser(userId: String, password: Array[Char], libraryCode: String): Try[String]
}

class AuthenticateServiceImpl(mongoClient: MongoClient) extends AuthenticateService{
  override def authenticateUser(userId: String, password: Array[Char], libraryCode: String): Try[String] = {
    Try {

    }
  }
}