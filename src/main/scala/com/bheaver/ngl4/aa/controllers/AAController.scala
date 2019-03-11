package com.bheaver.ngl4.aa.controllers

import com.bheaver.ngl4.aa.model.http.Library
import com.bheaver.ngl4.aa.services.LibraryService
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestMapping, RestController}
import reactor.core.publisher.{Flux, Mono}

@RestController
@RequestMapping(path = Array("/aa"))
class AAController {

  @Autowired
  @Qualifier("LibraryService")
  var librarySerivce: LibraryService = null;

  @GetMapping(path = Array("/listLibraries"))
  def listLibraries: Flux[Library] = {
    librarySerivce.getListLibraries
  }

}
