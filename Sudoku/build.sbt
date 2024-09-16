ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.0"

val zioVersion = "2.1.9"
val zioJsonVersion = "0.7.3"
val zioHttpVersion = "3.0.0-RC10"
val dependencies = Seq(
  "dev.zio" %% "zio"                % zioVersion,
  "dev.zio" %% "zio-streams"        % zioVersion,
  "dev.zio" %% "zio-http"           % zioHttpVersion,
  "dev.zio" %% "zio-json"           % zioJsonVersion,
  "dev.zio" %% "zio-test"           % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt"       % zioVersion % Test,
  "dev.zio" %% "zio-test-magnolia"  % zioVersion % Test
)

libraryDependencies := dependencies

lazy val root = (project in file("."))
  .settings(
    name := "Sudoku"
  )
