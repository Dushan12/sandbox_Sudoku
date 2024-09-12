package com.sudoku.services

import com.sudoku.enumerations.QuadrantsEnum
import com.sudoku.enumerations.QuadrantsEnum._
import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.utils.JsonUtils
import zio.{Runtime, Unsafe, ZIO}

object ValidationService {

  def isValidBoard(board: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    (for {
      isValidFormat <- ValidationService.isValidFormat(board)
      hasDuplicatesInRows <- ValidationService.duplicatesInRows(board)
      hasDuplicatesInColumns <- ValidationService.duplicatesInColumns(board)
      hasDuplicatesInQuadrants <- ValidationService.duplicatesInQuadrants(board)
    } yield {
      isValidFormat && !hasDuplicatesInRows && !hasDuplicatesInColumns && !hasDuplicatesInQuadrants
    })
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
    ZIO.succeed(sudokuBoard.items.map { columns =>
      columns.map(_.value).collect { case Some(value) => value }
    }.exists { columnValues =>
      columnValues.distinct.length != columnValues.length
    })
  }

  private def duplicatesInRows(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      invert <- invertColumnsAndRows(sudokuBoard)
      duplicates <- duplicatesInColumns(invert)
    } yield duplicates
  }

  private def duplicatesInQuadrants(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      quadrants <- ZIO.collectAll(QuadrantsEnum.values.map { quadrantName => getQuadrant(sudokuBoard, quadrantName) })
      existsDuplicateInQuadrant <- ZIO.succeed(quadrants
        .map(_.flatten.map(_.value).collect { case Some(value) => value })
        .map { exists => exists.length != exists.distinct.length })
    } yield {
      existsDuplicateInQuadrant.forall(identity)
    }
  }

  def invertColumnsAndRows(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard((0 to 8).toList.map { index =>
      sudokuBoard.items.map { columns =>
        columns(index)
      }
    }))
  }

  def getQuadrant(sudokuBoard: SudokuBoard, quadrant: QuadrantsEnum): ZIO[Any, Nothing, List[List[SudokuCell]]] = {
    quadrant match {
      case Quadrant1 => ZIO.succeed(
        sudokuBoard.items.slice(0,3).map { rows =>
          rows.slice(0,3)
        })
      case Quadrant2 => ZIO.succeed(
        sudokuBoard.items.slice(0,3).map { rows =>
          rows.slice(3,6)
        })
      case Quadrant3 => ZIO.succeed(
        sudokuBoard.items.slice(0,3).map { rows =>
          rows.slice(6,9)
        })
      case Quadrant4 => ZIO.succeed(
        sudokuBoard.items.slice(3,6).map { rows =>
          rows.slice(0,3)
        })
      case Quadrant5 => ZIO.succeed(
        sudokuBoard.items.slice(3,6).map { rows =>
          rows.slice(3,6)
        })
      case Quadrant6 => ZIO.succeed(
        sudokuBoard.items.slice(3,6).map { rows =>
          rows.slice(6,9)
        })
      case Quadrant7 => ZIO.succeed(
        sudokuBoard.items.slice(6,9).map { rows =>
          rows.slice(0,3)
        })
      case Quadrant8 => ZIO.succeed(
        sudokuBoard.items.slice(6,9).map { rows =>
          rows.slice(3,6)
        })
      case Quadrant9 => ZIO.succeed(
        sudokuBoard.items.slice(6,9).map { rows =>
          rows.slice(6,9)
        })
    }

  }

}
