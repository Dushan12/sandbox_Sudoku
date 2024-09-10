package com.sudoku.services

import com.sudoku.models.SudokuBoard
import zio.ZIO

class ValidationService {

  def validate(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {


    ZIO.succeed(sudokuBoard.items.flatMap(_.map(_.value)).exists { value =>
      value <= 0 && value > 9
    })
  }

}
