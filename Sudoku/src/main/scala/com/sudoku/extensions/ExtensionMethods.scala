package com.sudoku.extensions

import com.sudoku.enumerations.QuadrantsEnum
import com.sudoku.enumerations.QuadrantsEnum.*
import com.sudoku.models.{SudokuBoard, SudokuCell, SudokuCellMeta}
import zio.ZIO

extension (sudokuBoard: SudokuBoard)
  def invertColumnsAndRows: ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard((0 to 8).toList.map { index =>
      sudokuBoard.items.map { columns =>
        columns(index)
      }
    }))
  }

extension (sudokuBoard: SudokuBoard)
  def putElementOn(cellMeta: SudokuCellMeta, sudokuCell: SudokuCell): ZIO[Any, Nothing, SudokuBoard] = {
    ZIO.succeed(SudokuBoard(sudokuBoard.items.zipWithIndex.map { case (rows, rowIndex) =>
      rows.zipWithIndex.map { case (cell, colIndex) =>
        if (cellMeta.rowIndex == rowIndex && cellMeta.colIndex == colIndex)
          sudokuCell
        else
          cell
      }
    }))
  }

extension (sudokuBoard: SudokuBoard)
  def nextEmptyCell: ZIO[Any, Nothing, Option[SudokuCellMeta]] = {
    ZIO.succeed(sudokuBoard.items.zipWithIndex.flatMap { case (rows, rowIndex) =>
      rows.zipWithIndex.map { case (cell, colIndex) =>
        (cell.value, SudokuCellMeta(rowIndex,colIndex))
      }
    }.find(_._1.isEmpty).map(_._2))
  }

extension (input: List[SudokuCell])
  def getValues: List[Int] = {
    input.map(_.value).collect { case Some(x) => x }
  }

extension (sudokuBoard: SudokuBoard)
  def getColumns: ZIO[Any, Nothing, List[List[SudokuCell]]] = {
    ZIO.succeed(sudokuBoard.items)
  }

extension (sudokuBoard: SudokuBoard)
  def allCellsHaveValues: Boolean = {
    sudokuBoard.items.flatten.map(_.value.nonEmpty).forall(identity)
  }

extension (sudokuBoard: SudokuBoard)
  def getRows: ZIO[Any, Nothing, List[List[SudokuCell]]] = {
    sudokuBoard.invertColumnsAndRows.map(_.items)
  }

extension (sudokuBoard: SudokuBoard)
  def getQuadrant(quadrant: QuadrantsEnum): ZIO[Any, Nothing, List[List[SudokuCell]]] = {
    quadrant match {
      case Quadrant1 => ZIO.succeed(
        sudokuBoard.items.slice(0, 3).map { rows =>
          rows.slice(0, 3)
        })
      case Quadrant2 => ZIO.succeed(
        sudokuBoard.items.slice(0, 3).map { rows =>
          rows.slice(3, 6)
        })
      case Quadrant3 => ZIO.succeed(
        sudokuBoard.items.slice(0, 3).map { rows =>
          rows.slice(6, 9)
        })
      case Quadrant4 => ZIO.succeed(
        sudokuBoard.items.slice(3, 6).map { rows =>
          rows.slice(0, 3)
        })
      case Quadrant5 => ZIO.succeed(
        sudokuBoard.items.slice(3, 6).map { rows =>
          rows.slice(3, 6)
        })
      case Quadrant6 => ZIO.succeed(
        sudokuBoard.items.slice(3, 6).map { rows =>
          rows.slice(6, 9)
        })
      case Quadrant7 => ZIO.succeed(
        sudokuBoard.items.slice(6, 9).map { rows =>
          rows.slice(0, 3)
        })
      case Quadrant8 => ZIO.succeed(
        sudokuBoard.items.slice(6, 9).map { rows =>
          rows.slice(3, 6)
        })
      case Quadrant9 => ZIO.succeed(
        sudokuBoard.items.slice(6, 9).map { rows =>
          rows.slice(6, 9)
        })
    }

  }
