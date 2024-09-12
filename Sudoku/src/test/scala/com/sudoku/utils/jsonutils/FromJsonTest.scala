package com.sudoku.utils.jsonutils

import com.sudoku.models.SudokuBoard
import com.sudoku.factories.SudokuBoardFactory.*
import zio.{Scope, ZIO}
import zio.test.*
import zio.json.*

object FromJsonTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    suite("Sudoku -> Json Utilities -> fromJson -> Specs")(
      test("Generate Sudoku board from Json object") {
        for {
          input <- ZIO.succeed("""{"items":[[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}],[{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2},{"value":2}]]}""".stripMargin)
          expected <- generateBoardWithAllValuesEqual(Some(2))
          actual <-  ZIO.fromEither(input.fromJson[SudokuBoard])
        } yield {
          assertTrue(actual == expected)
        }
      }
    )
  }
}