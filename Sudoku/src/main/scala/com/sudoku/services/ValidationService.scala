package com.sudoku.services

import com.sudoku.models.SudokuBoard
import zio.ZIO

class ValidationService {

  def isValid(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {

    val hasInvalidNumbers = ZIO.succeed(sudokuBoard.items.flatMap(_.map(_.value)).exists { value =>
      value < 1 || value > 9
    })

    val invalidNumberOfColumns = ZIO.succeed(sudokuBoard.items.length != 9)

    val invalidNumberOfRows = ZIO.succeed(sudokuBoard.items.map(_.length).map { value => value == 9  }.exists(x => !x))

    for {
      invalidNumbers <- hasInvalidNumbers
      invalidColumns <- invalidNumberOfColumns
      invalidRows <- invalidNumberOfRows
    } yield (!(invalidNumbers || invalidColumns || invalidRows))


  }

}
