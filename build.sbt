name := "data-generator"

ThisBuild / scalaVersion := "2.12.2"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.serdeliverance"
ThisBuild / organizationName := "serdeliverance"

lazy val akkaVersion     = "2.6.8"
lazy val postgresVersion = "42.2.2"

lazy val root = project in file(".")

libraryDependencies ++= Seq(
  // log
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  // akka and alpakka
  "com.typesafe.akka"  %% "akka-actor-typed"          % akkaVersion,
  "com.typesafe.akka"  %% "akka-stream"               % akkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2",
  // DB
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick" %% "slick"          % "3.3.3",
  "org.postgresql"     % "postgresql"      % postgresVersion,
  // misc
  "com.github.javafaker" % "javafaker" % "1.0.2",
  // test
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "utf-8",
  "-explaintypes",
  "-feature",
  "-language:existentials",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked"
)

mainClass in (Compile, run) := Option("com.serdeliverance.infra.generator.UserTransactionGenerator")
