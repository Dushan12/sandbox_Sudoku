package com.sudoku.extensions.sudokucellmeta

import com.sudoku.enumerations.QuadrantsEnum
import com.sudoku.enumerations.QuadrantsEnum.{Quadrant1, Quadrant2, Quadrant3, Quadrant4, Quadrant5, Quadrant6, Quadrant7, Quadrant8, Quadrant9}
import com.sudoku.models.SudokuCellMeta
import zio.ZIO

extension (sudokuCellMeta: SudokuCellMeta)
  def getQuadrantForCellIndex: ZIO[Any, Nothing, QuadrantsEnum] = {
    ZIO.succeed(sudokuCellMeta match {
      case sudokuCellMeta if (sudokuCellMeta.rowIndex < 3 && sudokuCellMeta.colIndex < 3) =>
        Quadrant1
      case sudokuCellMeta if (sudokuCellMeta.rowIndex < 3 && sudokuCellMeta.colIndex >= 3 && sudokuCellMeta.colIndex < 6) =>
        Quadrant2
      case sudokuCellMeta if (sudokuCellMeta.rowIndex < 3 && sudokuCellMeta.colIndex >= 6 && sudokuCellMeta.colIndex < 9) =>
        Quadrant3
      case sudokuCellMeta if (sudokuCellMeta.rowIndex >= 3 && sudokuCellMeta.rowIndex < 6 && sudokuCellMeta.colIndex < 3) =>
        Quadrant4
      case sudokuCellMeta if (sudokuCellMeta.rowIndex >= 3 && sudokuCellMeta.rowIndex < 6 && sudokuCellMeta.colIndex >= 3 && sudokuCellMeta.colIndex < 6) =>
        Quadrant5
      case sudokuCellMeta if (sudokuCellMeta.rowIndex >= 3 && sudokuCellMeta.rowIndex < 6 && sudokuCellMeta.colIndex >= 6 && sudokuCellMeta.colIndex < 9) =>
        Quadrant6
      case sudokuCellMeta if (sudokuCellMeta.rowIndex >= 6 && sudokuCellMeta.rowIndex < 9 && sudokuCellMeta.colIndex < 3) =>
        Quadrant7
      case sudokuCellMeta if (sudokuCellMeta.rowIndex >= 6 && sudokuCellMeta.rowIndex < 9 && sudokuCellMeta.colIndex >= 3 && sudokuCellMeta.colIndex < 6) =>
        Quadrant8
      case _ =>
        Quadrant9
    })
  }