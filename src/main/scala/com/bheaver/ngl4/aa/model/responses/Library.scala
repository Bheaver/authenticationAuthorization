package com.bheaver.ngl4.aa.model.responses

import java.util

import com.bheaver.ngl4.aa.model.requests.LibraryRequest
import reactor.core.publisher.Flux

import scala.beans.BeanProperty

case class Library(@BeanProperty libraryCode: String, @BeanProperty libraryName: String)

case class LibraryResponse(@BeanProperty request: LibraryRequest, @BeanProperty  response: util.List[Library])