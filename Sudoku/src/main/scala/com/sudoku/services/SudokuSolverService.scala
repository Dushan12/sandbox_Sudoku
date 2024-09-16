package com.sudoku.services

import com.sudoku.enumerations.BoardStateActive.NotSolved
import com.sudoku.enumerations.BoardStateFinite.{Invalid, Solved}
import com.sudoku.enumerations.{BoardStateActive, BoardStateFinite}
import com.sudoku.extensions.*
import com.sudoku.extensions.sudokuboard.*
import com.sudoku.models.{SudokuBoard, SudokuCell}
import com.sudoku.services.ValidationService.*
import zio.{Unsafe, ZIO}

object SudokuSolverService {

  def solve(board: SudokuBoard): ZIO[Any, Nothing, (BoardStateFinite, SudokuBoard)] = {
    getBoardState(board).flatMap {
      case Invalid =>
        ZIO.succeed(Invalid, board)
      case Solved =>
        ZIO.succeed(Solved, board)
      case notSolved: NotSolved =>
        for {
          possibleNextValues <- board.getAllPossibleSolutionsForCell(notSolved.nextEmptyCell)
          possibleSolutions <- ZIO.collectAllPar(possibleNextValues.map { nextValue =>
            for {
              possibleNextStep <- board.putElementOn(notSolved.nextEmptyCell, SudokuCell(Some(nextValue)))
              isValidNextStepSolution <- areNumbersValid(possibleNextStep)
            } yield {
              if (isValidNextStepSolution)
                solve(possibleNextStep)
              else
                ZIO.succeed(Invalid, board)
            }
          })
          filterValidSolutions <- ZIO.collectAllPar(possibleSolutions).map(x => x.find {case (boardState, _) =>
            boardState == Solved
          }.toList)
          hasMoreThanOneSolution <- ZIO.succeed(filterValidSolutions.length > 1)
          getValidSolution <- ZIO.succeed(filterValidSolutions.headOption.map {
            result =>
              if(hasMoreThanOneSolution)
                (Invalid, board)
              else
                result
          }.getOrElse {
            (Invalid, board)
          })
        } yield (getValidSolution)
    }
  }

  private def getBoardState(board: SudokuBoard): ZIO[Any, Nothing, BoardStateFinite | BoardStateActive] = {
    board.nextEmptyCell.flatMap { nextEmptyCell =>
      nextEmptyCell.map(x => ZIO.succeed(NotSolved.apply(x))).getOrElse {
        areNumbersValid(board).map {
          case true =>
            Solved
          case false =>
            Invalid
        }
      }
    }
  }

}