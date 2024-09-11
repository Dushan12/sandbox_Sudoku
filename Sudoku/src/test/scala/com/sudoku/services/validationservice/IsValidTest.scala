package com.sudoku.services.validationservice

import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.services.ValidationService
import com.sudoku.utils.BoardGeneratorUtil._
import zio.test.*

object IsValidTest extends ZIOSpecDefault {
  def spec: Spec[Any, Nothing] = {
    val target = ValidationService
    suite("Sudoku -> Validate Service -> isValid -> Specs")(
     test("Return false when grid has less than 9 columns") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValid(input)
        } yield assertTrue(!actual)
      },
      test("Return false when grid has more than 9 columns") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValid(input)
        } yield assertTrue(!actual)
      },
      test("Return false when at least one column has more or less than 9 rows") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValid(input)
        } yield assertTrue(!actual)
      },
      test("Return false when at least one column has more or less than 9 rows") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(1))
          actual <- target.isValid(input)
        } yield assertTrue(!actual)
      },
      test("Return false when data is not valid (at least one number is not between 1 and 9)") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(0))
          actual <- target.isValid(input)
        } yield assertTrue(!actual)
      }
    )
  }
}
