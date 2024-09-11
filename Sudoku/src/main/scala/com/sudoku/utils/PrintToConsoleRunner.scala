package com.sudoku.utils

import com.sudoku.services.ValidationService
import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}


object PrintToConsoleRunner extends ZIOAppDefault{

  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] = {
    for {
      _ <- Console.printLine("============================================")
      _ <- Console.printLine("============================================")
      input <- BoardGeneratorUtil.generateBoardWithRandomValues()
      actual <- PrintUtil.generateAsciiBoard(input)
      _ <- Console.printLine(actual)
      _ <- Console.printLine("============================================")
      _ <- Console.printLine("============================================")
      inverted <- ValidationService.invertColumnsAndRows(input)
      invertedString <- PrintUtil.generateAsciiBoard(inverted)
      _ <- Console.printLine(invertedString)
      _ <- Console.printLine("============================================")
      _ <- Console.printLine("============================================")
      invertedJson <- JsonUtils.toJson(inverted)
      _ <- Console.printLine(invertedJson)
    } yield ()
  }
}