package com.sudoku.services

import com.sudoku.enumerations.{Invalid, NotSolved, Solved}
import com.sudoku.extensions.sudokuboard.*
import com.sudoku.extensions.*
import com.sudoku.models.{SudokuBoard, SudokuCell, SudokuCellMeta}
import com.sudoku.services.ValidationService.*
import zio.{Unsafe, ZIO}

object SudokuSolverService {

  def solve(board: SudokuBoard): ZIO[Any, Nothing, (Boolean,SudokuBoard)] = {
    getBoardState(board).flatMap {
      case _: Invalid =>
        ZIO.succeed(false, board)
      case _: Solved =>
        ZIO.succeed(true, board)
      case notSolved: NotSolved =>
        (for {
          possibleNextValues <- board.getAllPossibleSolutionsForCell(notSolved.nextEmptyCell)
          results <- ZIO.collectAll(possibleNextValues.map { nextValue =>
            for {
              possibleNextStep <- board.putElementOn(notSolved.nextEmptyCell, SudokuCell(Some(nextValue)))
              isValidNextStepSolution <- areNumbersValid(possibleNextStep)
            } yield {
              if (isValidNextStepSolution)
                solve(possibleNextStep)
              else
                ZIO.succeed(false, board)
            }
          })
          mapPossibleResult <- ZIO.collectAll(results)
        } yield mapPossibleResult).map { mapPossibleResult =>
          val possibleSolutions = mapPossibleResult.find(_._1).toList
          if(possibleSolutions.length > 1)
            (false, board)
          else {
            possibleSolutions.headOption.getOrElse {
              (false, board)
            }
          }
        }
    }
  }

  private def getBoardState(board: SudokuBoard): ZIO[Any, Nothing, Solved | NotSolved | Invalid] = {
    for {
      numbersAreValid <- areNumbersValid(board)
      nextEmptyCell <- board.nextEmptyCell
    } yield {
      if (numbersAreValid) {
        if (board.allCellsHaveValues)
          Solved()
        else nextEmptyCell.map(NotSolved.apply).getOrElse(Invalid())
      } else {
        Invalid()
      }
    }
  }

}