package com.sudoku.services.validationservice

import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.services.ValidationService
import zio.test.*

object ValidateTest extends ZIOSpecDefault {

  def spec: Spec[Any, Nothing] =
    suite("Sudoku -> Validate Service -> Specs")(
      test("Return false when data is not valid (at least one number is not between 1 and 9)") {

        val target = new ValidationService()

        val input = SudokuBoard(List.fill(9)(List.fill(9)(SudokuCell(0))))

        for {
          actual <- target.validate(input)
        } yield assertTrue(!actual)

      }
    )
}