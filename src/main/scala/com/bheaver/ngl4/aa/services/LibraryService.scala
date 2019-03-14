package com.bheaver.ngl4.aa.services

import java.util
import java.util.stream.Collectors

import com.bheaver.ngl4.aa.model.requests.LibraryRequest
import com.bheaver.ngl4.aa.model.responses.{Library, LibraryResponse}
import com.bheaver.ngl4.util.constants.MongoMasterCollections
import com.bheaver.ngl4.util.mongoUtils.Database
import org.bson.Document
import reactor.core.publisher.{Flux, Mono}


trait LibraryService {
  def getListLibraries(request: LibraryRequest): Mono[LibraryResponse]
}

class LibraryServiceImpl(database: Database) extends LibraryService {
  override def getListLibraries(request: LibraryRequest): Mono[LibraryResponse] = {
    val masterDatabase = database.getMasterDatabase
    val fluxLib: Flux[Document] = Flux.from(masterDatabase.getCollection(MongoMasterCollections.MASTERLIBRARYACCESSINFO).find())
    val xxx: Flux[Library] = fluxLib.map(document => Library(document.getString("config_libraryCode"), document.getString("config_libraryName")))
    val yyy = xxx.collectList()
    yyy.map(libraryList => {
      val llr = LibraryResponse(request,libraryList)
      llr
    })

  }
}