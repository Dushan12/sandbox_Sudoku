package com.sudoku.extensions.sudokuboard

import com.sudoku.extensions.sudokuboard.NextEmptyCellTest.{suite, test}
import com.sudoku.models.{SudokuBoard, SudokuCell, SudokuCellMeta}
import com.sudoku.services.SudokuValidationService
import zio.{Scope, ZIO}
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}
import zio.json.*
import com.sudoku.extensions.*

object PutElementOnTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    val target = SudokuValidationService
    suite("Sudoku -> SudokuBoard -> Extensions -> nextEmptyCell -> Specs")(
      test("return board with element placed on position 0 2") {
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
          changedBoard <- inputBoard.putElementOn(SudokuCellMeta(0, 2), SudokuCell(Some(3)))
        } yield assertTrue(changedBoard.toJson == """{"items":[
            |[{},{},{"value":3},{},{"value":5},{},{"value":9},{"value":2},{}],
            |[{"value":1},{},{},{},{"value":4},{"value":2},{"value":7},{"value":6},{"value":3}],
            |[{"value":9},{},{"value":2},{},{},{"value":7},{},{},{"value":5}],
            |[{},{},{},{},{},{"value":3},{"value":1},{"value":5},{"value":7}],
            |[{},{"value":5},{},{"value":6},{},{"value":9},{},{"value":8},{}],
            |[{},{},{},{"value":5},{"value":7},{},{},{},{}],
            |[{"value":5},{},{},{},{"value":9},{"value":8},{"value":6},{},{"value":2}],
            |[{},{"value":2},{"value":7},{"value":3},{},{"value":1},{},{},{"value":9}],
            |[{},{"value":4},{"value":9},{"value":7},{},{},{"value":8},{"value":3},{}]
            |]}""".stripMargin.replaceAll("\\s", "")
        )
      },
      test("return board with element placed on position 3 0") {
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
          changedBoard <- inputBoard.putElementOn(SudokuCellMeta(3, 0), SudokuCell(Some(4)))
        } yield assertTrue(changedBoard.toJson == """{"items":[
                                                    |[{},{},{},{},{"value":5},{},{"value":9},{"value":2},{}],
                                                    |[{"value":1},{},{},{},{"value":4},{"value":2},{"value":7},{"value":6},{"value":3}],
                                                    |[{"value":9},{},{"value":2},{},{},{"value":7},{},{},{"value":5}],
                                                    |[{"value":4},{},{},{},{},{"value":3},{"value":1},{"value":5},{"value":7}],
                                                    |[{},{"value":5},{},{"value":6},{},{"value":9},{},{"value":8},{}],
                                                    |[{},{},{},{"value":5},{"value":7},{},{},{},{}],
                                                    |[{"value":5},{},{},{},{"value":9},{"value":8},{"value":6},{},{"value":2}],
                                                    |[{},{"value":2},{"value":7},{"value":3},{},{"value":1},{},{},{"value":9}],
                                                    |[{},{"value":4},{"value":9},{"value":7},{},{},{"value":8},{"value":3},{}]
                                                    |]}""".stripMargin.replaceAll("\\s", "")
        )
      })
  }
}
