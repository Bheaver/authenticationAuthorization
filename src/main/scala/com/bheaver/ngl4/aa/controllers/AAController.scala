package com.bheaver.ngl4.aa.controllers

import com.bheaver.ngl4.aa.model.requests.LibraryRequest
import com.bheaver.ngl4.aa.model.responses.LibraryResponse
import com.bheaver.ngl4.aa.services.LibraryService
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.web.bind.annotation._
import reactor.core.publisher.{Flux, Mono}

@RestController
@RequestMapping(path = Array("/aa"))
class AAController {

  @Autowired
  @Qualifier("LibraryService")
  var librarySerivce: LibraryService = null;

  @GetMapping(path = Array("/listLibraries"), produces = Array("application/json;charset=UTF-8"))
  @ResponseBody
  def listLibraries: Mono[LibraryResponse] = {
    librarySerivce.getListLibraries(LibraryRequest()).map(libr => {
      libr
    })
  }

}
