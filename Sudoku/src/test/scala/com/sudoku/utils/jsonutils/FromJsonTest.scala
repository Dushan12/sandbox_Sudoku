package com.sudoku.utils.jsonutils

import com.sudoku.models.SudokuBoard
import com.sudoku.utils.BoardGeneratorUtil.*
import com.sudoku.utils.JsonUtils
import zio.ZIO
import zio.test.*

object FromJsonTest extends ZIOSpecDefault {
  def spec: Spec[Any, Nothing] = {
    suite("Sudoku -> Json Utilities -> fromJson -> Specs")(
      test("Generate Sudoku board from Json object") {
        for {
          input <- ZIO.succeed("""{"items":[[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}]]}""".stripMargin)
          expected <- generateBoardWithAllValuesEqual(Some(2))
          actual <- JsonUtils.fromJson(input).orElseSucceed(ZIO.succeed(""))
        } yield {
          assertTrue(actual == expected)
        }
      }
    )
  }
}