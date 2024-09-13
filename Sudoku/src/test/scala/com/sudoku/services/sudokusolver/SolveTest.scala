package com.sudoku.services.sudokusolver

import com.sudoku.models.SudokuBoard
import com.sudoku.services.SudokuSolverService
import com.sudoku.services.validationservice.IsValidFormatTest.suite
import zio.json.*
import zio.test.*
import zio.{Console, Scope, ZIO}

object SolveTest  extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    suite("Sudoku -> SudokuSolver -> Solve -> Specs")(
      test("Return solved board") {
        for {
          input <- ZIO.succeed(
            """{"items":[
              |[{},{},{},{},{"value":5},{},{"value":9},{"value":2},{}],
              |[{"value":1},{},{},{},{"value":4},{"value":2},{"value":7},{"value":6},{"value":3}],
              |[{"value":9},{},{"value":2},{},{},{"value":7},{},{},{"value":5}],
              |[{},{},{},{},{},{"value":3},{"value":1},{"value":5},{"value":7}],
              |[{},{"value":5},{},{"value":6},{},{"value":9},{},{"value":8},{}],
              |[{},{},{},{"value":5},{"value":7},{},{},{},{}],
              |[{"value":5},{},{},{},{"value":9},{"value":8},{"value":6},{},{"value":2}],
              |[{},{"value":2},{"value":7},{"value":3},{},{"value":1},{},{},{"value":9}],
              |[{},{"value":4},{"value":9},{"value":7},{},{},{"value":8},{"value":3},{}]
              |]}""".stripMargin)
          inputBoard <- ZIO.fromEither(input.fromJson[SudokuBoard])
          textInput <- ZIO.succeed(s"""|""" +
            inputBoard.items.flatten.zipWithIndex.map { case (x, index) =>
              val printValue = x.value.map(_.toString).getOrElse(" ")
              if ((index + 1) % 9 == 0 && index != 80)
                s"""$printValue|\n|"""
              else
                s"""$printValue|"""
            }.mkString(""))
          _ <- Console.printLine(textInput)
          _ <- Console.printLine("*" * 20)
          solvedBoard <- SudokuSolverService.solve(inputBoard)
          text <- ZIO.succeed(s"""|""" +
            solvedBoard._2.items.flatten.zipWithIndex.map { case (x, index) =>
              val printValue = x.value.map(_.toString).getOrElse(" ")
              if ((index + 1) % 9 == 0 && index != 80)
                s"""$printValue|\n|"""
              else
                s"""$printValue|"""
            }.mkString(""))
          _ <- Console.printLine(text)
        } yield assertTrue(true)
      }
    )
  }
}
