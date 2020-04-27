val ScalatraVersion = "2.7.0"
val Json4sVersion = "3.6.7"

organization := "com.brimborion"

name := "Library Demo"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.1"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s"   %% "json4s-jackson" % Json4sVersion,
  "org.json4s" %% "json4s-ext" % Json4sVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.27.v20200227" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "com.atlassian.oai" % "swagger-request-validator-core" % "2.9.0",
  "org.scalamock" %% "scalamock" % "4.4.0" % "test",
  "com.fullfacing" %% "keycloak4s-admin-monix" % "2.1.0",
  "com.fullfacing" %% "sttp-akka-monix" % "1.1.0",
  "com.softwaremill.sttp.client" %% "async-http-client-backend-cats" % "2.0.7"
)

enablePlugins(ScalatraPlugin)
