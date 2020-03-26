val ScalatraVersion = "2.5.4"

organization := "com.brimborion"

name := "Library Demo"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.12"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-json" % ScalatraVersion,
  "org.json4s"   %% "json4s-jackson" % "3.5.0",
  "org.json4s" %% "json4s-ext" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.4.19.v20190610" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.scalamock" %% "scalamock" % "4.4.0" % "test"
)

enablePlugins(ScalatraPlugin)
