import scala.collection.immutable.Seq

ThisBuild / version := "1.0.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
        .settings(
            name := "lincalc-scala",
            idePackagePrefix := Some("pl.piasta.lincalc.scala"),
            javaOptions += "-Dnashorn.args=--no-deprecation-warning",
            scalacOptions += "-release:11",
            libraryDependencies ++= Seq(
                "org.scalafx" % "scalafx_3" % "19.0.0-R30",
                "ch.obermuhlner" % "big-math" % "2.3.2"
            )
        )
