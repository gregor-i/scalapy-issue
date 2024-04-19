ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.3"
fork := true

libraryDependencies ++= Seq[ModuleID](
  // removing the following line fixes the issue:
  "dev.scalapy" %% "scalapy-macros" % "0.5.3",
  "dev.scalapy" %% "scalapy-core" % "0.5.3"
)


javaOptions ++= Def.task {
  import sys.process._

  val pythonLibPath =
    try
      Seq(
        "python3",
        "-c",
        "from distutils.sysconfig import get_config_var; print(get_config_var(\"LIBDIR\"))",
      ).!!.trim
    catch {
      case exception: Throwable =>
        throw new Exception(
          """Python lib path could not be determined!
            |Make sure that the executable 'python3' is in scope.""".stripMargin,
          exception,
        )
    }

  val properties = Seq(
    "scalapy.python.programname" -> "./venv/bin/python",
    "scalapy.python.library" -> "python3.9",
    "jna.library.path" -> pythonLibPath,
  )

  properties.map { case (key, value) => s"-D$key=$value" }
}.value
