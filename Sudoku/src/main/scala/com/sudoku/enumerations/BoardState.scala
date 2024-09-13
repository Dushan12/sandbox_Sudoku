package com.sudoku.enumerations

import com.sudoku.models.SudokuCellMeta



case class BSolved()
case class BInvalid()
case class BPartiallySolved(nextCell: SudokuCellMeta)

enum BoardState:
  case Solved, PartiallySolved, Invalid
