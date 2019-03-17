package com.bheaver.ngl4.aa.model.responses

import com.bheaver.ngl4.util.model.ErrorResponse

import scala.beans.BeanProperty

case class Patron(@BeanProperty patronId: String, @BeanProperty  patronName: String)
case class AuthenticateResponse (@BeanProperty authenticated:Boolean,
                                 @BeanProperty  jwtToken: String,
                                 @BeanProperty  patron: Patron,
                                @BeanProperty errorResponse: ErrorResponse) {

}
