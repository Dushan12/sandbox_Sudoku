package com.sudoku.utils.printutil

import com.sudoku.models.SudokuBoard
import com.sudoku.services.SudokuValidationService
import com.sudoku.services.sudokuvalidationservice.GetQuadrantTest.{suite, test}
import com.sudoku.utils.SudokuPrint
import zio.test.*
import zio.{Console, ZIO, *}
import zio.json.*

object GenerateAsciiBoardTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    val target = SudokuValidationService
    suite("Sudoku -> Print Util -> generateAsciiBoard -> Specs")(
      test("Generate ascii text for printing of the board") {
        for {
          inputString <- ZIO.succeed("""{"items":[[{"value":9},{"value":6},{"value":5},{"value":9},{"value":8},{"value":1},{"value":7},{"value":6},{"value":9}],[{"value":4},{"value":6},{"value":2},{"value":3},{"value":6},{"value":9},{"value":8},{"value":8},{"value":6}],[{"value":8},{"value":2},{"value":9},{"value":4},{"value":7},{"value":3},{"value":2},{"value":3},{"value":3}],[{"value":2},{"value":3},{"value":6},{"value":9},{"value":3},{"value":1},{"value":9},{"value":7},{"value":3}],[{"value":1},{"value":4},{"value":6},{"value":5},{"value":8},{"value":9},{"value":7},{"value":8},{"value":1}],[{"value":2},{"value":9},{"value":8},{"value":6},{"value":5},{"value":8},{"value":2},{"value":4},{"value":6}],[{"value":4},{"value":9},{"value":6},{"value":8},{"value":6},{"value":9},{"value":9},{"value":6},{"value":5}],[{"value":9},{"value":5},{"value":8},{"value":2},{"value":2},{"value":7},{"value":1},{"value":2},{"value":2}],[{"value":5},{"value":3},{"value":1},{"value":7},{"value":3},{"value":2},{"value":8},{"value":6},{"value":1}]]}""".stripMargin)
          sudokuBoardParsed <- ZIO.fromEither(inputString.fromJson[SudokuBoard])
          text <- SudokuPrint.generateAscii(sudokuBoardParsed)
          _ <- Console.printLine(text)
          actual <- TestConsole.output
        } yield assertTrue(actual.head ==
"""|9|6|5|9|8|1|7|6|9|
|4|6|2|3|6|9|8|8|6|
|8|2|9|4|7|3|2|3|3|
|2|3|6|9|3|1|9|7|3|
|1|4|6|5|8|9|7|8|1|
|2|9|8|6|5|8|2|4|6|
|4|9|6|8|6|9|9|6|5|
|9|5|8|2|2|7|1|2|2|
|5|3|1|7|3|2|8|6|1|
"""
        )
      },
    )
  }
}