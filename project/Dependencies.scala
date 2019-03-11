import sbt._

object Versions {
  val mongoReactiveStreams = "1.10.0"
  val reactiveStreams = "1.0.2"
  val mongoDriverVersion = "3.9.1"
  val springBootWebFluxVersion = "2.1.3.RELEASE"
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

  val coreDependencies = Seq(springBootWebFlux, reactiveMongoStreams,mongoAsyncCore,reactiveStreams,mongoJavaDriver)

}
object NGLDependencies {
  val util = "com.bheaver.ngl4" %% "util" % NGLVersions.globalVersion

  val nglDependencies = Seq(util)
}