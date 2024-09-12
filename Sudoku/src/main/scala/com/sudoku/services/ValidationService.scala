package com.sudoku.services

import com.sudoku.*
import com.sudoku.enumerations.QuadrantsEnum
import com.sudoku.models.{SudokuBoard, SudokuCell}
import zio.{Unsafe, ZIO}
import com.sudoku.extensions._

object ValidationService {

  def isValidBoard(board: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      isValidFormat <- isValidFormat(board)
      hasDuplicatesInRows <- duplicatesInRows(board)
      hasDuplicatesInColumns <- duplicatesInColumns(board)
      hasDuplicatesInQuadrants <- duplicatesInQuadrants(board)
    } yield {
      isValidFormat && !hasDuplicatesInRows && !hasDuplicatesInColumns && !hasDuplicatesInQuadrants
    }
  }

  def isValidFormat(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    ZIO.succeed(
        sudokuBoard.items.length == 9 &&
          sudokuBoard.items.map { rows =>
            rows.length == 9 &&
              rows.getValues.map { cellValue =>
                cellValue >= 1 || cellValue <= 9
              }.forall(identity)
          }.forall(identity)
      )
  }

  private def duplicatesInColumns(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      columns <- sudokuBoard.getColumns
    } yield {
      columns.map { columns =>
        columns.getValues
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
        .map(_.flatten.getValues)
        .map { exists => exists.length != exists.distinct.length })
    } yield {
      existsDuplicateInQuadrant.forall(identity)
    }
  }

}
