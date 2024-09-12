package com.sudoku.utils.jsonutils

import com.sudoku.factories.SudokuBoardFactory.*
import zio.ZIO
import zio.test.*
import zio.json.*

object ToJsonTest extends ZIOSpecDefault {
  def spec: Spec[Any, Nothing] = {
    suite("Sudoku -> Json Utilities -> toJson -> Specs")(
      test("Return json string from the sudoku board model") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(2))
          actual <-  ZIO.succeed(input.toJson) //JsonUtils.toJson(input)
        } yield {
          assertTrue(actual == """{"items":[[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}]]}""".stripMargin)
        }
      },
      test("Return json string from the sudoku empty board model") {
        for {
          input <- generateEmptyBoard()
          actual <- ZIO.succeed(input.toJson)
        } yield {
          assertTrue(actual == """{"items":[[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}],[{},{},{},{},{},{},{},{},{}]]}""".stripMargin)
        }
      }
    )
  }
}