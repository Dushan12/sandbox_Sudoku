package com.sudoku.extensions.sudokuboard

import com.sudoku.models.{SudokuBoard, SudokuCellMeta}
import com.sudoku.services.ValidationService
import com.sudoku.services.validationservice.GetQuadrantTest.{suite, test}
import zio.test.*
import zio.{Console, ZIO, *}
import zio.json.*
import com.sudoku.extensions.*

object NextEmptyCellTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    val target = ValidationService
    suite("Sudoku -> SudokuBoard -> Extensions -> nextEmptyCell -> Specs")(
      test("Return cell metadata for the next empty cell") {
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
          actual <- inputBoard.nextEmptyCell
        } yield assertTrue(actual.contains(SudokuCellMeta(0, 0)))
      },
      test("Return cell metadata for the next empty cell") {
        for {
          input <- ZIO.succeed(
            """{"items":[
              |[{"value":1},{},{},{},{"value":5},{},{"value":9},{"value":2},{}],
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
          actual <- inputBoard.nextEmptyCell
        } yield assertTrue(actual.contains(SudokuCellMeta(0, 1)))
      },
      test("Return cell metadata for the next empty cell") {
        for {
          input <- ZIO.succeed(
            """{"items":[
              |[{"value":1},{"value":1},{"value":1},{"value":1},{"value":5},{"value":1},{"value":9},{"value":2},{"value":1}],
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
          actual <- inputBoard.nextEmptyCell
        } yield assertTrue(actual.contains(SudokuCellMeta(1, 1)))
      }
    )
  }
}