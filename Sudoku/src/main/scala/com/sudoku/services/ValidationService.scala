package com.sudoku.services

import com.sudoku.models.{SudokuBoard, SudokuCell}
import zio.ZIO

sealed trait Quadrant

final case class Quadrant1() extends Quadrant
final case class Quadrant2() extends Quadrant
final case class Quadrant3() extends Quadrant
final case class Quadrant4() extends Quadrant
final case class Quadrant5() extends Quadrant
final case class Quadrant6() extends Quadrant
final case class Quadrant7() extends Quadrant
final case class Quadrant8() extends Quadrant
final case class Quadrant9() extends Quadrant



object ValidationService {

  def isFilled(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      valid <- isValid(sudokuBoard)
      containsEmptyElements <- ZIO.succeed(sudokuBoard.items.flatten.exists(_.value.isEmpty))
    } yield (valid && !containsEmptyElements)
  }

  def isValid(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {

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
      val columnValues = columns.map(_.value).collect { case Some(value) => value }
      columnValues.distinct.length == columnValues.length
    }.forall(identity))
  }

  def invertColumnsAndRows(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard((0 to 8).toList.map { index =>
      sudokuBoard.items.map { columns =>
        columns(index)
      }
    }))
  }

  private def duplicatesInRows(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
      for {
      invert <- invertColumnsAndRows(sudokuBoard)
      duplicates <- duplicatesInColumns(invert)
    } yield duplicates
  }

  def getQuadrant(sudokuBoard: SudokuBoard, quadrant: Quadrant): ZIO[Any, Nothing, List[List[SudokuCell]]] = {
    quadrant match {
      case com.sudoku.services.Quadrant1() => ZIO.succeed(
            sudokuBoard.items.slice(0,3).map { rows =>
              rows.slice(0,3)
            })
      case com.sudoku.services.Quadrant2() => ZIO.succeed(
          sudokuBoard.items.slice(0,3).map { rows =>
            rows.slice(3,6)
          })

      case com.sudoku.services.Quadrant3() => ZIO.succeed(
        sudokuBoard.items.slice(0,3).map { rows =>
          rows.slice(6,9)
        })
      case com.sudoku.services.Quadrant4() => ZIO.succeed(
          sudokuBoard.items.slice(0,3).map { rows =>
            rows.slice(3,5)
          })
      case com.sudoku.services.Quadrant5() => ZIO.succeed(
        sudokuBoard.items.slice(4,6).map { rows =>
          rows.slice(3,5)
        })
      case com.sudoku.services.Quadrant6() => ZIO.succeed(
        sudokuBoard.items.slice(7,9).map { rows =>
          rows.slice(3,5)
        })
      case com.sudoku.services.Quadrant7() => ZIO.succeed(
        sudokuBoard.items.slice(0,2).map { rows =>
          rows.slice(6,8)
        })
      case com.sudoku.services.Quadrant8() => ZIO.succeed(
        sudokuBoard.items.slice(3,5).map { rows =>
          rows.slice(6,8)
        })
      case com.sudoku.services.Quadrant9() => ZIO.succeed(
        sudokuBoard.items.slice(6,8).map { rows =>
          rows.slice(6,8)
        })
    }

  }

}
