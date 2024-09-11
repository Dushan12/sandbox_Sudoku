package com.sudoku.services.validationservice

import com.sudoku.services.ValidationService
import zio.test.*
import zio.{Console, Scope, ZIO}

object IsValidBoardTest extends ZIOSpecDefault {
  def spec: Spec[TestEnvironment & Scope, Any] = {
    val target = ValidationService
    suite("Sudoku -> Validate Service -> isValidBoard -> Specs")(
      test("return true because the board is valid") {
        for {
          input <- ZIO.succeed("""{"items":[
                                 |[{},{},{},{},{"value":5},{},{"value":9},{"value":2},{}],
                                 |[{"value":1},{},{},{},{"value":4},{"value":2},{"value":7},{"value":6},{"value":3}],
                                 |[{"value":9},{},{"value":2},{},{},{"value":7},{},{},{"value":5}],
                                 |[{},{},{},{},{},{"value":3},{"value":1},{"value":5},{"value":7}],
                                 |[{},{"value":5},{},{"value":6},{},{"value":9},{},{"value":8},{}],
                                 |[{},{},{},{"value":5},{"value":7},{},{},{},{}],
                                 |[{"value":5},{},{},{},{"value":9},{"value":8},{"value":6},{},{"value":2}],
                                 |[{},{"value":2},{"value":7},{"value":3},{},{"value":1},{},{},{"value":9}],
                                 |[{},{"value":4},{"value":9},{"value":7},{},{},{"value":8},{"value":3},{}]
                                 |]}""".stripMargin)
          actual <- target.isValidBoard(input)
        } yield assertTrue(actual)
      },

      test("Return false because the board has two 9's in the first quadrant") {
        for {
          input <- ZIO.succeed("""{"items":[
                                 |[{},{},{},{},{"value":5},{},{"value":9},{"value":2},{}],
                                 |[{"value":1},{},{},{},{"value":4},{"value":2},{"value":7},{"value":6},{"value":3}],
                                 |[{"value":9},{"value":9},{"value":2},{},{},{"value":7},{},{},{"value":5}],
                                 |[{},{},{},{},{},{"value":3},{"value":1},{"value":5},{"value":7}],
                                 |[{},{"value":5},{},{"value":6},{},{"value":9},{},{"value":8},{}],
                                 |[{},{},{},{"value":5},{"value":7},{},{},{},{}],
                                 |[{"value":5},{},{},{},{"value":9},{"value":8},{"value":6},{},{"value":2}],
                                 |[{},{"value":2},{"value":7},{"value":3},{},{"value":1},{},{},{"value":9}],
                                 |[{},{"value":4},{"value":9},{"value":7},{},{},{"value":8},{"value":3},{}]
                                 |]}""".stripMargin)
          actual <- target.isValidBoard(input)
          _ <- Console.printLine(actual)
        } yield assertTrue(!actual)
      },
    )
  }
}