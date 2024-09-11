package com.sudoku.services.validationservice

import com.sudoku.models.SudokuCell
import com.sudoku.services.{Quadrant1, Quadrant2, Quadrant3, ValidationService}
import com.sudoku.utils.{JsonUtils, PrintToConsoleRunner, PrintUtil}
import zio.{Console, ZIO, *}
import zio.test.*

object GetQuadrantTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    val target = ValidationService
    suite("Sudoku -> Validate Service -> getQuadrant -> Specs")(
      test("Return first quadrant") {
        for {
          inputString <- ZIO.succeed("""{"items":[[{"value":9},{"value":6},{"value":5},{"value":9},{"value":8},{"value":1},{"value":7},{"value":6},{"value":9}],[{"value":4},{"value":6},{"value":2},{"value":3},{"value":6},{"value":9},{"value":8},{"value":8},{"value":6}],[{"value":8},{"value":2},{"value":9},{"value":4},{"value":7},{"value":3},{"value":2},{"value":3},{"value":3}],[{"value":2},{"value":3},{"value":6},{"value":9},{"value":3},{"value":1},{"value":9},{"value":7},{"value":3}],[{"value":1},{"value":4},{"value":6},{"value":5},{"value":8},{"value":9},{"value":7},{"value":8},{"value":1}],[{"value":2},{"value":9},{"value":8},{"value":6},{"value":5},{"value":8},{"value":2},{"value":4},{"value":6}],[{"value":4},{"value":9},{"value":6},{"value":8},{"value":6},{"value":9},{"value":9},{"value":6},{"value":5}],[{"value":9},{"value":5},{"value":8},{"value":2},{"value":2},{"value":7},{"value":1},{"value":2},{"value":2}],[{"value":5},{"value":3},{"value":1},{"value":7},{"value":3},{"value":2},{"value":8},{"value":6},{"value":1}]]}""".stripMargin)
          sudokuBoardParsed <- JsonUtils.fromJson(inputString)
          firstQuadrant <- ValidationService.getQuadrant(sudokuBoardParsed, Quadrant1())
          expected <- ZIO.succeed(
            List(List(SudokuCell(Some(9)), SudokuCell(Some(6)), SudokuCell(Some(5))), List(SudokuCell(Some(4)), SudokuCell(Some(6)), SudokuCell(Some(2))), List(SudokuCell(Some(8)), SudokuCell(Some(2)), SudokuCell(Some(9))))
          )
        } yield assertTrue(firstQuadrant == expected)
      },
      test("Return second quadrant") {
        for {
          inputString <- ZIO.succeed("""{"items":[[{"value":9},{"value":6},{"value":5},{"value":9},{"value":8},{"value":1},{"value":7},{"value":6},{"value":9}],[{"value":4},{"value":6},{"value":2},{"value":3},{"value":6},{"value":9},{"value":8},{"value":8},{"value":6}],[{"value":8},{"value":2},{"value":9},{"value":4},{"value":7},{"value":3},{"value":2},{"value":3},{"value":3}],[{"value":2},{"value":3},{"value":6},{"value":9},{"value":3},{"value":1},{"value":9},{"value":7},{"value":3}],[{"value":1},{"value":4},{"value":6},{"value":5},{"value":8},{"value":9},{"value":7},{"value":8},{"value":1}],[{"value":2},{"value":9},{"value":8},{"value":6},{"value":5},{"value":8},{"value":2},{"value":4},{"value":6}],[{"value":4},{"value":9},{"value":6},{"value":8},{"value":6},{"value":9},{"value":9},{"value":6},{"value":5}],[{"value":9},{"value":5},{"value":8},{"value":2},{"value":2},{"value":7},{"value":1},{"value":2},{"value":2}],[{"value":5},{"value":3},{"value":1},{"value":7},{"value":3},{"value":2},{"value":8},{"value":6},{"value":1}]]}""".stripMargin)
          sudokuBoardParsed <- JsonUtils.fromJson(inputString)
          firstQuadrant <- ValidationService.getQuadrant(sudokuBoardParsed, Quadrant2())
          expected <- ZIO.succeed(
            List(List(SudokuCell(Some(9)), SudokuCell(Some(8)), SudokuCell(Some(1))), List(SudokuCell(Some(3)), SudokuCell(Some(6)), SudokuCell(Some(9))), List(SudokuCell(Some(4)), SudokuCell(Some(7)), SudokuCell(Some(3))))
          )
        } yield assertTrue(firstQuadrant == expected)
      },

      test("Return third quadrant") {
        for {
          inputString <- ZIO.succeed("""{"items":[[{"value":9},{"value":6},{"value":5},{"value":9},{"value":8},{"value":1},{"value":7},{"value":6},{"value":9}],[{"value":4},{"value":6},{"value":2},{"value":3},{"value":6},{"value":9},{"value":8},{"value":8},{"value":6}],[{"value":8},{"value":2},{"value":9},{"value":4},{"value":7},{"value":3},{"value":2},{"value":3},{"value":3}],[{"value":2},{"value":3},{"value":6},{"value":9},{"value":3},{"value":1},{"value":9},{"value":7},{"value":3}],[{"value":1},{"value":4},{"value":6},{"value":5},{"value":8},{"value":9},{"value":7},{"value":8},{"value":1}],[{"value":2},{"value":9},{"value":8},{"value":6},{"value":5},{"value":8},{"value":2},{"value":4},{"value":6}],[{"value":4},{"value":9},{"value":6},{"value":8},{"value":6},{"value":9},{"value":9},{"value":6},{"value":5}],[{"value":9},{"value":5},{"value":8},{"value":2},{"value":2},{"value":7},{"value":1},{"value":2},{"value":2}],[{"value":5},{"value":3},{"value":1},{"value":7},{"value":3},{"value":2},{"value":8},{"value":6},{"value":1}]]}""".stripMargin)
          sudokuBoardParsed <- JsonUtils.fromJson(inputString)
          textAscii <- PrintUtil.generateAsciiBoard(sudokuBoardParsed)
          _ <- Console.printLine(textAscii)
          firstQuadrant <- ValidationService.getQuadrant(sudokuBoardParsed, Quadrant3())
          expected <- ZIO.succeed(
            List(List(SudokuCell(Some(7)), SudokuCell(Some(6)), SudokuCell(Some(9))), List(SudokuCell(Some(8)), SudokuCell(Some(8)), SudokuCell(Some(6))), List(SudokuCell(Some(2)), SudokuCell(Some(3)), SudokuCell(Some(3))))
          )
        } yield assertTrue(firstQuadrant == expected)
      },
    )
  }
}
