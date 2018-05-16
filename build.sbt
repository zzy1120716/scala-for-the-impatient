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

lazy val ch08 = (project in file("ch08"))
  .settings(
    commonSettings
  )

lazy val ch09 = (project in file("ch09"))
  .settings(
    commonSettings
  )

lazy val ch10 = (project in file("ch10"))
  .settings(
    commonSettings
  )

lazy val ch11 = (project in file("ch11"))
  .settings(
    commonSettings
  )

lazy val ch12 = (project in file("ch12"))
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
  ch08,
  ch09,
  ch10,
  ch11,
  ch12,
  example
)
