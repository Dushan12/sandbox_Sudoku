package com.sudoku.services.validationservice

import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.services.ValidationService
import com.sudoku.utils.BoardGeneratorUtil._
import zio.test.*

object IsFilledTest extends ZIOSpecDefault {
  def spec: Spec[Any, Nothing] = {
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
      }
    )
  }
}
