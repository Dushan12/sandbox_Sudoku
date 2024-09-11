package com.sudoku.utils

import com.sudoku.models.SudokuBoard
import zio.{IO, ZIO}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}
import zio.json.*

class JsonUtils {
  def toJson(board: SudokuBoard): ZIO[Any, Nothing, String] = {
    ZIO.succeed(board.toJson)
  }

  def fromJson(jsonString: String): ZIO[Any, String, SudokuBoard] = {
    ZIO.fromEither(
      jsonString.fromJson[SudokuBoard]
    )
  }
}
