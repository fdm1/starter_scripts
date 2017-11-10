lazy val commonSettings = Seq(
  name := "foobar",
  scalaVersion := "2.11.8",
  version := "0.1",
  organization := "com.frankmassi",
  test in assembly := {}
)

lazy val projectDependencies = Seq(
    libraryDependencies ++= Seq(

    "org.apache.spark" %% "spark-core" % "2.2.0" % "provided",
    "org.apache.spark" %% "spark-streaming" % "2.2.0" % "provided"
    )

)

lazy val testDependencies = Seq(
  libraryDependencies ++= Seq(

    "org.mockito" % "mockito-all" % "1.9.5" % "test",
    "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test",
    "junit" % "junit" % "4.10" % "test"
    // ,
    // // used for style-checking submissions
    // "org.scalastyle" %% "scalastyle" % "0.8.0"
  )
)

lazy val assemblySettings = Seq(
    assemblyMergeStrategy in assembly := {
      case PathList(xs @ _*) if xs.last == "UnusedStubClass.class" => MergeStrategy.first
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    },

    unmanagedBase := baseDirectory.value / "external_jars",
    target in assembly := baseDirectory.value / "compiled_jars"
)

lazy val app_builder = (project in file(".")).
  settings(commonSettings: _*).
  settings(projectDependencies: _*).
  settings(testDependencies: _*).
  settings(assemblySettings: _*)
