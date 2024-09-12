package com.sudoku.services.validationservice

import com.sudoku.services.ValidationService
import com.sudoku.generators.SudokuBoardGenerator.*
import com.sudoku.models.SudokuBoard
import zio.test.*
import zio.{Scope, ZIO}
import zio.json.*

object IsValidFormatTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    val target = ValidationService
    suite("Sudoku -> Validate Service -> isValid -> Specs")(
     test("Return false when grid has less than 9 columns") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValidFormat(input)
        } yield assertTrue(!actual)
      },
      test("Return false when grid has more than 9 columns") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValidFormat(input)
        } yield assertTrue(!actual)
      },
      test("Return false when at least one column has more or less than 9 rows") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValidFormat(input)
        } yield assertTrue(!actual)
      },
      test("Return false when at least one column has more or less than 9 rows") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValidFormat(input)
        } yield assertTrue(!actual)
      },
      test("Return false when data is not valid (at least one number is not between 1 and 9)") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(0))
          actual <- target.isValidFormat(input)
        } yield assertTrue(!actual)
      },
      test("Return true when data is valid") {
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
          actual <- target.isValidFormat(inputBoard)
        } yield assertTrue(actual)
      }
    )
  }
}
