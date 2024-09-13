package com.sudoku.enumerations

import com.sudoku.models.SudokuCellMeta

enum BoardStateActive(nextEmptyCell: SudokuCellMeta):
  case NotSolved(nextEmptyCell: SudokuCellMeta) extends BoardStateActive(nextEmptyCell)