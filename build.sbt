import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "PPS-24-papyrus",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )

libraryDependencies ++= Seq(
  "io.cucumber" % "cucumber-scala_2.13" % "8.17.0" % Test,
  "io.cucumber" % "cucumber-junit"      % "7.14.0" % Test,
  "org.junit.vintage" % "junit-vintage-engine" % "5.9.3" % Test,
  "junit" % "junit" % "4.13.2" % Test
)

testFrameworks += new TestFramework("io.cucumber.junit.Cucumber")
