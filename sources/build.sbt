name := """spicy-world"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.typesafe.slick" %% "slick" % "latest.release"
)

libraryDependencies += "com.typesafe.slick" %% "slick" % "latest.release"
libraryDependencies += "mysql" % "mysql-connector-java" % "latest.release"
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
