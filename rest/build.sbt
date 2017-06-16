
name := "projectx-rest"

version := "1.0"

scalaVersion := "2.11.7"

organization := "com.projectx"

val akkaV = "10.0.7"
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.1.1",
  "com.typesafe.akka" %% "akka-http-core" % akkaV,
  "com.typesafe.akka" %% "akka-http" % akkaV,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaV % "test",
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaV,
  "org.scalatest"     %% "scalatest" % "3.0.3" % "test",
  "com.couchbase.client" %% "spark-connector" % "2.0.0"
)

assembleArtifact in packageScala := false // We don't need the Scala library, Spark already includes it

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
fork in run := true
