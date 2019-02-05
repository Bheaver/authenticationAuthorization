package com.bheaver.ngl4.aa.exceptions

object AuthenticationException{
  val INVALID_USERID_PASSWORD = "Invalid userid and password"
}
class AuthenticationException(message: String) extends RuntimeException{

}
