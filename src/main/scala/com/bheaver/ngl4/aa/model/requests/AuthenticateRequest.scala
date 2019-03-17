package com.bheaver.ngl4.aa.model.requests

import scala.beans.BeanProperty

case class AuthenticateRequest(username: String, password: String, libCode:String)

class AuthenticateRequestHttpBody {

  @BeanProperty
  var requestId:String = null

  @BeanProperty
  var libCode:String = null

  @BeanProperty
  var username: String = null

  @BeanProperty
  var password: String = null
}