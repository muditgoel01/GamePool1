import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "GamePool1"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    javaCore,
    "com.github.tuxBurner" %% "play-neo4jplugin" % "1.3.5"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "tuxburner.github.io" at "http://tuxburner.github.io/repo",
    resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
    resolvers += "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/",
    resolvers += "Spring milestones" at "http://repo.spring.io/milestone",
    resolvers += "Open Geo" at "http://repo.opengeo.org/",
    resolvers += "OS Geo" at "http://download.osgeo.org/webdav/geotools/"
  )

}




