package com.sudoku.models

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class SudokuBoard(items: List[List[SudokuCell]])

object SudokuBoard {
  implicit val decoder: JsonDecoder[SudokuBoard] = DeriveJsonDecoder.gen[SudokuBoard]
  implicit val encoder: JsonEncoder[SudokuBoard] = DeriveJsonEncoder.gen[SudokuBoard]
}