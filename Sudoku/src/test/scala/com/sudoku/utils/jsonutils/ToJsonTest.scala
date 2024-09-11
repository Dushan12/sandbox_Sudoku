package com.sudoku.utils.jsonutils


import com.sudoku.services.ValidationService
import com.sudoku.utils.BoardGeneratorUtil.*
import com.sudoku.utils.JsonUtils
import zio.test.*

object ToJsonTest extends ZIOSpecDefault {
  def spec: Spec[Any, Nothing] = {
    suite("Sudoku -> Json Utilities -> toJson -> Specs")(
      test("Return json string from the sudoku board model") {
        for {
          input <- generateBoardWithAllValuesEqual(Some(2))
          actual <- JsonUtils.toJson(input)
        } yield {
          assertTrue(actual == """{"items":[[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}]]}""".stripMargin)
        }
      }
    )
  }
}