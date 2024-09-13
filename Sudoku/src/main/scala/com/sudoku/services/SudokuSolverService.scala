package com.sudoku.services

import com.sudoku.enumerations.{BInvalid, BPartiallySolved, BSolved}
import com.sudoku.extensions.*
import com.sudoku.models.{SudokuBoard, SudokuCell, SudokuCellMeta}
import com.sudoku.services.ValidationService.*
import zio.{Unsafe, ZIO}

object SudokuSolverService {

  def getBoardState(board: SudokuBoard): ZIO[Any, Nothing, BSolved | BPartiallySolved | BInvalid] = {
    for {
      numbersAreValid <- areNumbersValid(board)
      nextEmptyCell <- board.nextEmptyCell
    } yield {
      if (numbersAreValid) {
        if (board.allCellsHaveValues)
          BSolved()
        else nextEmptyCell.map(BPartiallySolved.apply).getOrElse(BInvalid())
      } else {
        BInvalid()
      }
    }
  }

  def solve(board: SudokuBoard): ZIO[Any, Nothing, (Boolean,SudokuBoard)] = {


    val dd: ZIO[Any, Nothing, (Boolean,SudokuBoard)] = getBoardState(board).flatMap {
        case bi: BInvalid =>
          ZIO.succeed(false, board)
        case bs: BSolved =>
          ZIO.succeed(true, board)
        case bps: BPartiallySolved =>
          for {
            results <- ZIO.collectAll((1 to 9).map { nextValue =>
              for {
                possibleNextStep <- board.putElementOn(bps.nextCell, SudokuCell(Some(nextValue)))
                isValid <- areNumbersValid(possibleNextStep)
              } yield {
                if (isValid)
                  solve(possibleNextStep)
                else
                  ZIO.succeed(false, board)
              }
            })
            mapPossibleResult <- ZIO.collectAll(results)

          } yield {
            mapPossibleResult.toList.findLast(x => x._1).map(x =>
              x
            ).getOrElse {
              (false, board)
            }
          }
    }
    dd
    /*
    val dull = for {
      boardState <-  getBoardState(board)
    } yield {
      val dd: ZIO[Any, Nothing, (Boolean, SudokuBoard)] = boardState match {
        case bi: BInvalid =>
          val du = ZIO.succeed(false, board)
          du
        case bs: BSolved =>
          val de = ZIO.succeed(true, board)
          de
        case bps: BPartiallySolved =>
          for {
            results <- ZIO.collectAll((1 to 9).map { nextValue =>
              for {
                possibleNextStep <- board.putElementOn(bps.nextCell, SudokuCell(Some(nextValue)))
                isValid <- areNumbersValid(possibleNextStep)
              } yield {
                if(isValid)
                  solve(possibleNextStep)
                else
                  ZIO.succeed(false, board)
              }
            })
            mapPossibleResult <- ZIO.collectAll(results)

          } yield {
            mapPossibleResult.toList.findLast(x => x._1).map(x =>
              x
            ).getOrElse {
              (false, board)
            }
          }
      }
      dd
    }
    dull*/
  }

}