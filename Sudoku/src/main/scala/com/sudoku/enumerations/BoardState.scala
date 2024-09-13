package com.sudoku.enumerations

import com.sudoku.models.SudokuCellMeta

case class Solved()
case class Invalid()
case class NotSolved(nextEmptyCell: SudokuCellMeta)

