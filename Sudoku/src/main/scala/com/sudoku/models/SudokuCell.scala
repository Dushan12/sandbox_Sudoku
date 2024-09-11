package com.sudoku.models

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class SudokuCell(value: Option[Int])

object SudokuCell {
  implicit val decoder: JsonDecoder[SudokuCell] = DeriveJsonDecoder.gen[SudokuCell]
  implicit val encoder: JsonEncoder[SudokuCell] = DeriveJsonEncoder.gen[SudokuCell]
}