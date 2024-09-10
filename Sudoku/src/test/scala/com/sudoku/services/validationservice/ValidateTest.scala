package com.sudoku.services.validationservice

import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.services.ValidationService
import zio.test.*

object ValidateTest extends ZIOSpecDefault {

  def spec: Spec[Any, Nothing] =
    suite("Sudoku -> Validate Service -> Specs")(

     test("Return false when grid has less than 9 columns") {

        val target = new ValidationService()

        val input = SudokuBoard(List.fill(8)(List.fill(9)(SudokuCell(1))))

        for {
          actual <- target.isValid(input)
        } yield assertTrue(!actual)

      },

      test("Return false when grid has more than 9 columns") {

        val target = new ValidationService()

        val input = SudokuBoard(List.fill(10)(List.fill(9)(SudokuCell(1))))

        for {
          actual <- target.isValid(input)
        } yield assertTrue(!actual)

      },

      test("Return false when at least one column has more or less than 9 rows") {

        val target = new ValidationService()

        val input = SudokuBoard(List.fill(9)(List.fill(8)(SudokuCell(1))))

        for {
          actual <- target.isValid(input)
        } yield assertTrue(!actual)

      },

      test("Return false when at least one column has more or less than 9 rows") {

        val target = new ValidationService()

        val input = SudokuBoard(List.fill(9)(List.fill(10)(SudokuCell(1))))

        for {
          actual <- target.isValid(input)
        } yield assertTrue(!actual)

      },

      test("Return false when data is not valid (at least one number is not between 1 and 9)") {

        val target = new ValidationService()

        val input = SudokuBoard(List.fill(9)(List.fill(9)(SudokuCell(0))))

        for {
          actual <- target.isValid(input)
        } yield assertTrue(!actual)

      }
    )
}