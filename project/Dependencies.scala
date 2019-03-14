import sbt._

object Versions {
  val mongoReactiveStreams = "1.10.0"
  val reactiveStreams = "1.0.2"
  val mongoDriverVersion = "3.9.1"
  val springBootWebFluxVersion = "2.1.3.RELEASE"
  val log4j2Version = "2.11.2"
  val log4j2ScalaAPIVersion = "11.0"
}

object NGLVersions {
  val globalVersion = "0.0.1-SNAPSHOT"
}

object Dependencies {

  val springBootWebFlux = "org.springframework.boot" % "spring-boot-starter-webflux" % Versions.springBootWebFluxVersion

  val reactiveMongoStreams = "org.mongodb" % "mongodb-driver-reactivestreams" % Versions.mongoReactiveStreams
  val mongoAsyncCore = "org.mongodb" % "mongodb-driver-async" % Versions.mongoDriverVersion
  val reactiveStreams = "org.reactivestreams" % "reactive-streams" % Versions.reactiveStreams
  val mongoJavaDriver = "org.mongodb" % "mongo-java-driver" % Versions.mongoDriverVersion

  val log4j2ScalaAPI = "org.apache.logging.log4j" %% "log4j-api-scala" % Versions.log4j2ScalaAPIVersion
  val log4j2Core = "org.apache.logging.log4j" % "log4j-core" % Versions.log4j2Version
  val log4j2API = "org.apache.logging.log4j" % "log4j-api" % Versions.log4j2Version
  val guava = "com.google.guava" % "guava" % "27.1-jre"
  val coreDependencies = Seq(springBootWebFlux, reactiveMongoStreams,mongoAsyncCore,reactiveStreams,mongoJavaDriver, log4j2API,log4j2Core,log4j2ScalaAPI,guava)

}
object NGLDependencies {
  val util = "com.bheaver.ngl4" %% "util" % NGLVersions.globalVersion

  val nglDependencies = Seq(util)
}