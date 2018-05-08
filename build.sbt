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
    commonSettings,
  )

lazy val ch02 = (project in file("ch02"))
  .settings(
    commonSettings,
  )

lazy val ch03 = (project in file("ch03"))
  .settings(
    commonSettings,
  )

lazy val ch04 = (project in file("ch04"))
  .settings(
    commonSettings,
  )

lazy val ch05 = (project in file("ch05"))
  .settings(
    commonSettings,
  )

lazy val ch06 = (project in file("ch06"))
  .settings(
    commonSettings,
  )

lazy val ch07 = (project in file("ch07"))
  .settings(
    commonSettings
  )

lazy val example = (project in file("example"))
  .settings(
    commonSettings
  )

lazy val aggregatedProjects: Seq[ProjectReference] = Seq(
  ch01,
  ch02,
  ch03,
  ch04,
  ch05,
  ch06,
  ch07,
  example
)
