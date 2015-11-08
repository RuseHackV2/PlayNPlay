name := """/home/omisoft/dev/MediaIntegration"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
javaJpa
)

libraryDependencies += "org.eclipse.persistence" % "org.eclipse.persistence.jpa" % "2.6.0"
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.1"





// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
