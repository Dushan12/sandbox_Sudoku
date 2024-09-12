package com.sudoku.utils

import com.sudoku.models.SudokuBoard
import zio.ZIO
import zio.json.*

object JsonUtils {
  def toJson(board: SudokuBoard): ZIO[Any, Nothing, String] = {
    ZIO.succeed(board.toJson)
  }

  def fromJson(jsonString: String): ZIO[Any, String, SudokuBoard] = {
    ZIO.fromEither(
      jsonString.fromJson[SudokuBoard]
    )
  }
}
