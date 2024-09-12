package com.sudoku.services

import com.sudoku.*
import com.sudoku.enumerations.QuadrantsEnum
import com.sudoku.models.{SudokuBoard, SudokuCell}
import zio.{Unsafe, ZIO}
import com.sudoku.extensions._

object ValidationService {

  def isValidBoard(board: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      isValidFormat <- ValidationService.isValidFormat(board)
      hasDuplicatesInRows <- ValidationService.duplicatesInRows(board)
      hasDuplicatesInColumns <- ValidationService.duplicatesInColumns(board)
      hasDuplicatesInQuadrants <- ValidationService.duplicatesInQuadrants(board)
    } yield {
      isValidFormat && !hasDuplicatesInRows && !hasDuplicatesInColumns && !hasDuplicatesInQuadrants
    }
  }

  def isFilled(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      valid <- isValidFormat(sudokuBoard)
      containsEmptyElements <- ZIO.succeed(sudokuBoard.items.flatten.exists(_.value.isEmpty))
    } yield (valid && !containsEmptyElements)
  }

  def isValidFormat(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {

    for {
      invalidBoard <- ZIO.succeed(
        sudokuBoard.items.length != 9 ||
          sudokuBoard.items.exists { rows =>
            rows.length != 9 ||
              rows.map(_.value).collect { case Some(x) => x }.exists { cellValue =>
                cellValue < 1 || cellValue > 9
              }
          }
      )
    } yield (!invalidBoard)
  }

  private def duplicatesInColumns(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      columns <- sudokuBoard.getColumns
    } yield {
      columns.map { columns =>
        columns.map(_.value).collect { case Some(value) => value }
      }.exists { columnValues =>
        columnValues.distinct.length != columnValues.length
      }
    }
  }

  private def duplicatesInRows(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      invert <- sudokuBoard.invertColumnsAndRows
      duplicates <- duplicatesInColumns(invert)
    } yield duplicates
  }

  private def duplicatesInQuadrants(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      quadrants <- ZIO.collectAll(QuadrantsEnum.values.map(sudokuBoard.getQuadrant))
      existsDuplicateInQuadrant <- ZIO.succeed(quadrants
        .map(_.flatten.map(_.value).collect { case Some(value) => value })
        .map { exists => exists.length != exists.distinct.length })
    } yield {
      existsDuplicateInQuadrant.forall(identity)
    }
  }

}
