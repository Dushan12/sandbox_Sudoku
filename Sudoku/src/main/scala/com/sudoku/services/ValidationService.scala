package com.sudoku.services

import com.sudoku.enumerations.{Quadrant, Quadrants}
import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.enumerations.Quadrants.*
import com.sudoku.utils.JsonUtils
import zio.{Runtime, Unsafe, ZIO}







object ValidationService {

  def isValidBoard(boardJsonString: String): ZIO[Any, String, Boolean] = {
    (for {
      bodyStr <- ZIO.succeed(boardJsonString)
      parseBodyAsSudokuBoard <- JsonUtils.fromJson(bodyStr)
      isValidFormat <- ValidationService.isValidFormat(parseBodyAsSudokuBoard)
      hasDuplicatesInRows <- ValidationService.duplicatesInRows(parseBodyAsSudokuBoard)
      hasDuplicatesInColumns <- ValidationService.duplicatesInColumns(parseBodyAsSudokuBoard)
      hasDuplicatesInQuadrants <- ValidationService.duplicatesInQuadrants(parseBodyAsSudokuBoard)
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
      val columnValues = columns.map(_.value).collect { case Some(value) => value }
      columnValues.distinct.length == columnValues.length
    }.forall(identity))
  }

  private def duplicatesInRows(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    for {
      invert <- invertColumnsAndRows(sudokuBoard)
      duplicates <- duplicatesInColumns(invert)
    } yield duplicates
  }

  private def duplicatesInQuadrants(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, Boolean] = {
    ZIO.succeed(Quadrants.getAll.map { quadrantName =>
      Unsafe.unsafe { implicit unsafe =>
        Runtime.default.unsafe.run(getQuadrant(sudokuBoard, quadrantName)).getOrElse(_ => List.empty[List[SudokuCell]])
      }
    }.exists { quadrantElements =>
      quadrantElements.length != quadrantElements.distinct.length
    })
  }

  def invertColumnsAndRows(sudokuBoard: SudokuBoard): ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard((0 to 8).toList.map { index =>
      sudokuBoard.items.map { columns =>
        columns(index)
      }
    }))
  }

  def getQuadrant(sudokuBoard: SudokuBoard, quadrant: Quadrant): ZIO[Any, Nothing, List[List[SudokuCell]]] = {
    quadrant match {
      case Quadrant1() => ZIO.succeed(
            sudokuBoard.items.slice(0,3).map { rows =>
              rows.slice(0,3)
            })
      case Quadrant2() => ZIO.succeed(
          sudokuBoard.items.slice(0,3).map { rows =>
            rows.slice(3,6)
          })
      case Quadrant3() => ZIO.succeed(
        sudokuBoard.items.slice(0,3).map { rows =>
          rows.slice(6,9)
        })
      case Quadrant4() => ZIO.succeed(
          sudokuBoard.items.slice(3,6).map { rows =>
            rows.slice(0,3)
          })
      case Quadrant5() => ZIO.succeed(
        sudokuBoard.items.slice(3,6).map { rows =>
          rows.slice(3,6)
        })
      case Quadrant6() => ZIO.succeed(
        sudokuBoard.items.slice(3,6).map { rows =>
          rows.slice(6,9)
        })
      case Quadrant7() => ZIO.succeed(
        sudokuBoard.items.slice(6,9).map { rows =>
          rows.slice(0,3)
        })
      case Quadrant8() => ZIO.succeed(
        sudokuBoard.items.slice(6,9).map { rows =>
          rows.slice(3,6)
        })
      case Quadrant9() => ZIO.succeed(
        sudokuBoard.items.slice(6,9).map { rows =>
          rows.slice(6,9)
        })
    }

  }

}
