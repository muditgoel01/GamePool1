name := "GamePool1"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

libraryDependencies += "org.neo4j" % "neo4j-spatial" % "0.13-neo4j-2.0.1"


play.Project.playJavaSettings
