package com.sudoku.utils

import com.sudoku.models.{SudokuBoard, SudokuCell}
import zio.{Random, ZIO}
import zio.Runtime

object BoardGeneratorUtil {
  def generateFullBoardWithCustomElementOnIndex(item: SudokuCell, inputColumn: Int, inputRow: Int): ZIO[Any, Nothing, SudokuBoard] =  {
    ZIO.succeed(SudokuBoard(((0 to 8) map { column =>
      ((0 to 8) map { row =>
        if(column == inputColumn && row == inputRow)
          item
        else
          SudokuCell(Some(1))
      }).toList
    }).toList))
  }

  def generateEmptyBoard(): ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard(List.fill(8)(List.fill(9)(SudokuCell(None)))))
  }

  def generateBoardWithAllValuesEqual(value: Option[Int]): ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard(List.fill(8)(List.fill(9)(SudokuCell(value)))))
  }

  def generateBoardWithRandomValues(): ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard(((0 to 8) map { column =>
      ((0 to 8) map { row =>
        SudokuCell(Some(Runtime.default.unsafe.run(Random.nextIntBetween(1, 10)).getOrElse(_ => 0)))
      }).toList
    }).toList))


  }

}
