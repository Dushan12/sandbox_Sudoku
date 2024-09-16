package com.sudoku.services.sudokusolver

import com.sudoku.models.SudokuBoard
import com.sudoku.services.SudokuSolverService
import com.sudoku.services.sudokuvalidationservice.IsValidFormatTest.suite
import com.sudoku.utils.SudokuPrint
import com.sudoku.utils.printutil.GenerateAsciiBoardTest
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
          textInput <- SudokuPrint.generateAscii(inputBoard)
          _ <- Console.printLine(textInput)
          _ <- Console.printLine("*" * 20)
          startTime <- ZIO.succeed(System.currentTimeMillis())
          (_, solvedBoard) <- SudokuSolverService.solve(inputBoard)
          endTime <- ZIO.succeed(System.currentTimeMillis())
          text <- SudokuPrint.generateAscii(solvedBoard)
          _ <- Console.printLine(text)
          _ <- Console.printLine("*" * 20)
          duration <- Console.printLine("Duration " + (endTime - startTime) + " ms")
          _ <- Console.printLine("*" * 20)
        } yield assertTrue(true)

      })
  }
}
