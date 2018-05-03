// dependencies
val scalatest = "org.scalatest" %% "scalatest" % "3.0.5" % "test"
val scalaParserCombinators = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0"

lazy val commonSettings = Seq(
  organization := "com.bupt.zzy",
  version := "0.1",
  scalaVersion := "2.12.6",
  libraryDependencies ++= Seq(
    scalatest
  )
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "scala-for-the-impatient",
    libraryDependencies ++= Seq(
      scalaParserCombinators
    )
  )
  .aggregate(aggregatedProjects: _*)

lazy val ch01 = (project in file("ch01"))
  .settings(
    commonSettings
  )

lazy val example = (project in file("example"))
  .settings(
    commonSettings
  )

lazy val aggregatedProjects: Seq[ProjectReference] = Seq(
  ch01,
  example
)
