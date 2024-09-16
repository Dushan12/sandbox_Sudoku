# Sudoku Solver Application
 1. Language:  
  - Scala3
  - ZIO (Http, Json, Test)
  
## Discussion
The proposed solution is immutable and recursive

## Current Implementation
1. API for validating a puzzle (It will validate the puzzle without stating the reason if it fails)
2. API for solving a puzzle (It will return a solution or fail)

## Further Improvements
1. Create front-end application that will use the API
  - Generate puzzle
    * Generate random board
    * Remove random values and validate if the state of the board has only one solution
    * When no additional numbers can be removed without making the board ambiguous
    * Return the puzzle
  - Preview the solution (Call the API to solve the puzzle)
  - Play the game
    * Validate every move with reason why the move is not valid
    

## Benchmark
- Time to solve puzzle 
  * AMD Ryzen™ 7 7730U ~= 1849 ms 
