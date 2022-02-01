name := "ScalaJsonFlattener"

version := "0.1"

scalaVersion := "2.13.4"

val simulacrum = "org.typelevel" %% "simulacrum" % "1.0.1"
val kindProjector = compilerPlugin("org.typelevel" % "kind-projector" % "0.11.3" cross CrossVersion.full)
val resetAllAttrs = "org.scalamacros" %% "resetallattrs" % "1.0.0"
val jsonsmart = "net.minidev" % "json-smart" % "2.4.7"
val commonsText = "org.apache.commons" % "commons-text" % "1.9"
val commonslang = "org.apache.commons" % "commons-lang3" % "3.12.0"
val playjson = "com.typesafe.play" %% "play-json" % "2.10.0-RC5"
val upicklejson = "com.lihaoyi" %% "upickle" % "1.4.3"
val jackson = "com.fasterxml.jackson.module" % "jackson-module-scala" % "2.0.2"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "something",
    libraryDependencies ++= Seq(
      jackson,
      simulacrum,
      kindProjector,
      resetAllAttrs,
      jsonsmart,
      commonsText,
      commonslang,
      playjson,
      upicklejson
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:_"
    )
  )