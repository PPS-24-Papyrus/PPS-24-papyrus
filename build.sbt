import sbt.Keys.libraryDependencies

ThisBuild / version := "2.0.1"

ThisBuild / scalaVersion := "3.3.6"

Compile / packageBin / artifactName := { (_, module, _) =>
  s"$name-$version.jar"
}


lazy val root = (project in file("."))
  .settings(
    name := "PPS-24-papyrus",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test,
    libraryDependencies += "io.github.iltotore" %% "iron" % "2.4.0",
    libraryDependencies += "com.openhtmltopdf" % "openhtmltopdf-pdfbox" % "1.0.10" % Provided,
    libraryDependencies += "org.xhtmlrenderer" % "flying-saucer-pdf" % "9.1.22" % Provided,
    libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.2",
    libraryDependencies += "io.cucumber" % "cucumber-junit" % "7.14.0" % Test,
    libraryDependencies += "it.unibo.alice.tuprolog" % "tuprolog" % "3.3.0"

  )

libraryDependencies ++= Seq(
  "io.cucumber" % "cucumber-scala_2.13" % "8.17.0" % Test,
  "io.cucumber" % "cucumber-junit"      % "7.14.0" % Test,
  "org.junit.vintage" % "junit-vintage-engine" % "5.9.3" % Test,
  "junit" % "junit" % "4.13.2" % Test
)
