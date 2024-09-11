package com.sudoku.services

import com.sudoku.models.SudokuBoard
import zio.ZIO

object ValidationService {

  def isFilled(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      valid <- isValid(sudokuBoard)
      containsEmptyElements <- ZIO.succeed(sudokuBoard.items.flatten.exists(_.value.isEmpty))
    } yield (valid && !containsEmptyElements)
  }

  def isValid(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {

    for {
      invalidBoard <- ZIO.succeed(
        sudokuBoard.items.length != 9 ||
          sudokuBoard.items.exists { rows =>
            rows.length != 9 ||
              rows.map(_.value).collect { case Some(x) => x }.exists { cellValue =>
                cellValue < 1 || cellValue > 9
              }
          }
      )
    } yield (!invalidBoard)

  }

}
