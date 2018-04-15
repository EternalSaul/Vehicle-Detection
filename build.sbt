lazy val root = (project in file("."))
  .settings(
    name := "VehicleDetection",
    version := "0.1",
    scalaVersion := "2.12.2",
    libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",
    libraryDependencies += "com.github.wookietreiber" %% "scala-chart" % "latest.integration",
    libraryDependencies += "org.jfree" % "jfreesvg" % "3.0",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.0",
  )
