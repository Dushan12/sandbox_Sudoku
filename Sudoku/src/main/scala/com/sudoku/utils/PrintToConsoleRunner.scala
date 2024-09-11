package com.sudoku.utils

import zio.{Console, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}


object PrintToConsoleRunner extends ZIOAppDefault{

  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] = {

    val target = new PrintUtil()
    for {
      input <- BoardGeneratorUtil.generateBoardWithRandomValues()
      actual <- target.generateAsciiBoard(input)
      _ <- Console.printLine(actual)
    } yield ()
  }
}