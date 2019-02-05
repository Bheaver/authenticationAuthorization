import sbt._

object Versions {
  val springWebFluxVersion = "5.1.4.RELEASE"
  val mongoReactiveStreams = "1.10.0"
  val reactiveStreams = "1.0.2"
  val mongoDriverVersion = "3.9.1"
}

object Dependencies {

  val springWebFlux = "org.springframework" % "spring-webflux" % Versions.springWebFluxVersion

  val reactiveMongoStreams = "org.mongodb" % "mongodb-driver-reactivestreams" % Versions.mongoReactiveStreams
  val bson = "org.mongodb" % "bson" % Versions.mongoDriverVersion
  val mongoCore = "org.mongodb" % "mongodb-driver-core" % Versions.mongoDriverVersion
  val mongoAsyncCore = "org.mongodb" % "mongodb-driver-async" % Versions.mongoDriverVersion
  val reactiveStreams = "org.reactivestreams" % "reactive-streams" % Versions.reactiveStreams
  val mongoJavaDriver = "org.mongodb" % "mongo-java-driver" % Versions.mongoDriverVersion

  val coreDependencies = Seq(springWebFlux, reactiveMongoStreams,bson,mongoCore,mongoAsyncCore,reactiveStreams,mongoJavaDriver)

}