package com.sudoku.utils

import com.sudoku.models.SudokuBoard
import zio.ZIO

object SudokuPrint {
  def generateAscii(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, String] = {
    ZIO.succeed(
      s"""|""" +
        sudokuBoard.items.flatten.zipWithIndex.map { case (x, index) =>
          val printValue = x.value.map(_.toString).getOrElse(" ")
          if ((index + 1) % 9 == 0 && index != 80)
            s"""$printValue|\n|"""
          else
            s"""$printValue|"""
        }.mkString(""))
  }
}
