package com.sudoku.utils

import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}


object PrintToConsoleRunner extends ZIOAppDefault{

  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] = {
    for {
      input <- BoardGeneratorUtil.generateBoardWithRandomValues()
      actual <- PrintUtil.generateAsciiBoard(input)
      _ <- Console.printLine(actual)
    } yield ()
  }
}