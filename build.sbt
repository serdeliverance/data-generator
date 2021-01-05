name := "data-generator"

ThisBuild / scalaVersion     := "2.12.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.serdeliverance"
ThisBuild / organizationName := "serdeliverance"

lazy val akkaVersion    = "2.6.8"
lazy val postgresVersion  = "42.2.2"

lazy val root = project in file(".")


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
  "ch.qos.logback"    % "logback-classic"           % "1.2.3",

  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.postgresql"     % "postgresql"  % postgresVersion,

  "org.scalatest"     %% "scalatest"                % "3.0.8"         % Test
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

mainClass in (Compile, run) := Option("com.serdeliverance.DataGenerator")