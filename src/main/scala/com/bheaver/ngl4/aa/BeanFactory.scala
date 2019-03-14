package com.bheaver.ngl4.aa

import java.io.File

import com.bheaver.ngl4.aa.services.{AuthenticateService, AuthenticateServiceImpl, LibraryService, LibraryServiceImpl}
import com.bheaver.ngl4.util.mongoUtils.Database
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfigurationPackage
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

import scala.io.Source

@Configuration
@ComponentScan(basePackages = Array("com.bheaver.ngl4.util","com.bheaver.ngl4.util.filters"))
class BeanFactory {
  @Bean(Array("AuthenticateService"))
  def getAuthenticateService(@Qualifier("Database") database: Database): AuthenticateService = {
    new AuthenticateServiceImpl(database)
  }

  @Bean(Array("LibraryService"))
  def getLibraryService(@Qualifier("Database") database: Database): LibraryService = {
    new LibraryServiceImpl(database)
  }

  @Bean(Array("ApplicationYAMLString"))
  def getApplicationYAMLString(): String = {
    val strings = Source.fromResource("application.yaml").getLines()
    strings.toArray.mkString("\n")
  }
}
