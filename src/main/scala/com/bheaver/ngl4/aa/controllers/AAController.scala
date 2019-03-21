package com.bheaver.ngl4.aa.controllers

import com.bheaver.ngl4.aa.exceptions.AuthenticationException
import com.bheaver.ngl4.aa.model.requests.{AuthenticateRequest, AuthenticateRequestHttpBody, LibraryRequest}
import com.bheaver.ngl4.aa.model.responses.{AuthenticateResponse, LibraryResponse}
import com.bheaver.ngl4.aa.services.{AuthenticateService, LibraryService}
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.web.bind.annotation._
import reactor.core.publisher.Mono
import com.bheaver.ngl4.util.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.server.ServerWebExchange

import scala.util.{Failure, Success, Try}

@RestController
@RequestMapping(path = Array("/aa"))
class AAController {

  @Autowired
  @Qualifier("LibraryService")
  var librarySerivce: LibraryService = null;

  @Autowired
  @Qualifier("AuthenticateService")
  var authenticateService: AuthenticateService = null;

  @GetMapping(path = Array("/listLibraries"), produces = Array("application/json;charset=UTF-8"))
  @ResponseBody
  def listLibraries: Mono[LibraryResponse] = {
    librarySerivce.getListLibraries(LibraryRequest()).map(libr => {
      libr
    })
  }

  @PostMapping(path = Array("/authenticate"))
  def authenticateUser(@RequestBody authenticateRequestHttpBody: AuthenticateRequestHttpBody,
                       @RequestHeader("libCode") libCode: String,
                       exchange: ServerWebExchange): Mono[AuthenticateResponse] = {
      authenticateService.authenticateUser(AuthenticateRequest(authenticateRequestHttpBody.username,
        authenticateRequestHttpBody.password,
        authenticateRequestHttpBody.libCode)).map(eitherOfVal => {
        eitherOfVal match {
          case Left(value) => {
            exchange.getResponse.setStatusCode(HttpStatus.UNAUTHORIZED)
            AuthenticateResponse(false, null, null, ErrorResponse(401, "Authentication Error"))
          }
          case Right(value) => value
        }
      })



  }

}
