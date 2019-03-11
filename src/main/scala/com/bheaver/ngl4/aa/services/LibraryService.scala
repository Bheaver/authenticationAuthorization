package com.bheaver.ngl4.aa.services

import com.bheaver.ngl4.aa.model.http.Library
import com.bheaver.ngl4.util.constants.{MongoCollections, MongoMasterCollections}
import com.bheaver.ngl4.util.mongoUtils.Database
import reactor.core.publisher.{Flux, Mono}

trait LibraryService {
  def getListLibraries: Flux[Library]
}

class LibraryServiceImpl(database: Database) extends LibraryService {
  override def getListLibraries: Flux[Library] = {
    val masterDatabase = database.getMasterDatabase
    val collection = masterDatabase.getCollection(MongoMasterCollections.MASTERLIBRARYACCESSINFO)
    Flux.from(collection.find()).map(document => {
      Library(document.getString("config_libraryCode"), document.getString("config_libraryName"))
    })
  }
}