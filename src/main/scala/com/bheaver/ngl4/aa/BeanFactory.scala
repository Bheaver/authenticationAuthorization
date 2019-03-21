package com.bheaver.ngl4.aa

import com.bheaver.ngl4.aa.services._
import com.bheaver.ngl4.util.config.ApplicationConf
import com.bheaver.ngl4.util.mongoUtils.Database
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, DependsOn}

import scala.io.Source

@Configuration
@ComponentScan(basePackages = Array("com.bheaver.ngl4.util","com.bheaver.ngl4.util.filters","com.bheaver.ngl4.util.config"))
class BeanFactory {
  @Bean(Array("AuthenticateService"))
  @DependsOn(Array("JWTTool"))
  def getAuthenticateService(@Qualifier("Database") database: Database,
                             @Qualifier("JWTTool") jwtService: JWTService): AuthenticateService = {
    new AuthenticateServiceImpl(database,jwtService)
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
  @Bean(Array("JWTTool"))
  def getJSTTool(applicationConf: ApplicationConf): JWTService = {
    new JWTServiceImpl(applicationConf)
  }
}
