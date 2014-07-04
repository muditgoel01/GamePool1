name := "GamePool1"

version := "1.0-SNAPSHOT"

organization := "com.github.muditgoel01"

//scalaVersion := "2.10.2"


libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)

//libraryDependencies ++= Seq(
  //"com.typesafe.play" %% "play" % "2.2.3",
  //"com.typesafe.play" %% "play-java" % "2.2.3",
  // spring data neo4j dependencies
  //"javax.inject" % "javax.inject" % "1",
  //"asm" % "asm" % "3.3.1",
  //"com.sun.jersey" % "jersey-core" % "1.9",
  // spring data stuff
  //"org.springframework" % "spring-context" % "4.0.2.RELEASE",
  //"org.springframework.data" % "spring-data-neo4j" % "3.0.2.RELEASE",
  //"org.springframework.data" % "spring-data-neo4j-rest" % "3.0.2.RELEASE",
  // neo4j stuff
  //"org.neo4j" % "neo4j" % "2.0.3"
//)

libraryDependencies += "org.neo4j" % "neo4j-spatial" % "0.13-neo4j-2.0.1"

libraryDependencies += "commons-collections" % "commons-collections" % "3.0"


//resolvers ++= Seq(
//  "tuxburner.github.io" at "http://tuxburner.github.io/repo",
//  //"Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
//  //"Spring releases" at "http://repo.springsource.org/release",
//  "Spring milestones" at "http://repo.spring.io/milestone",
//  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
//  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/",
//  "Open Geo" at "http://repo.opengeo.org/",
//  "OS Geo" at "http://download.osgeo.org/webdav/geotools/"
//)

play.Project.playJavaSettings
