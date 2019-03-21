package com.bheaver.ngl4.aa.model.responses

import com.bheaver.ngl4.util.model.ErrorResponse
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

import scala.beans.BeanProperty

@JsonInclude(Include.NON_NULL)
case class Patron(@BeanProperty patronId: String,
                  @BeanProperty  patronName: String,
                  @BeanProperty  patronCategoryId: String)

@JsonInclude(Include.NON_NULL)
case class AuthenticateResponse (@BeanProperty authenticated:Boolean,
                                 @BeanProperty  jwtToken: String,
                                 @BeanProperty  patron: Patron,
                                @BeanProperty errorResponse: ErrorResponse) {

}
