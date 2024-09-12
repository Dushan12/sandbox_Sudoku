package com.sudoku.services.validationservice

import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.services.ValidationService
import com.sudoku.factories.SudokuBoardFactory.*
import zio.test.*
import zio.{Scope, ZIO}
import zio.json.*

object IsFilledTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    val target = ValidationService
    suite("Sudoku -> Validate Service -> isFilled -> Specs")(
      test("Return false when all elements in the sudoku board are empty") {
        for {
          input <- generateEmptyBoard()
          isSolved <- target.isFilled(input)
        } yield assertTrue(!isSolved)
      },
      test("Return false when at least one element in the board is empty") {
        for {
          input <- generateFullBoardWithCustomElementOnIndex(SudokuCell(None), 0, 0)
          isSolved <- target.isFilled(input)
        } yield assertTrue(!isSolved)
      },
      test("Return false when at least one element in the board is empty") {
        for {
          input <- ZIO.succeed("""{"items":[
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
          isSolved <- target.isFilled(inputBoard)
        } yield assertTrue(!isSolved)
      }
    )
  }
}
