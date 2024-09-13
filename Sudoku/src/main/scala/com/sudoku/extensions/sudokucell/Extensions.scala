package com.sudoku.extensions.sudokucell

import com.sudoku.models.SudokuCell

extension (input: List[SudokuCell])
  def getValues: List[Int] = {
    input.map(_.value).collect { case Some(x) => x }
  }
