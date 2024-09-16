import com.sudoku.models.SudokuBoard
import com.sudoku.services.{SudokuSolverService, SudokuValidationService}
import zio.*
import zio.http.*
import zio.json.*

object main extends ZIOAppDefault {

  private val routes: Routes[Any, Nothing] =
    Routes(
      Method.POST / "validate" -> handler { (req: Request) => validateRequestHandler(req) },
      Method.POST / "solve" -> handler { (req: Request) => solveRequestHandler(req) }
    )

  def run: ZIO[ZIOAppArgs & Scope, Any, Any] = Server.serve(routes).provide(Server.default)

  private def solveRequestHandler(req: Request): ZIO[Any, Nothing, Response] = {
    (for {
      bodyStr <- req.body.asString.mapError(_.getMessage)
      parseBodyAsSudokuBoard <- ZIO.fromEither(bodyStr.fromJson[SudokuBoard])
      (solved, solution) <- SudokuSolverService.solve(parseBodyAsSudokuBoard)
    } yield {
      Response.json(solution.toJson)
    }).catchAll { error =>
      ZIO.succeed(Response.error(
        zio.http.Status.InternalServerError,
        s"""Error while generating board! ERROR: $error"""
      ))
    }
  }

  private def validateRequestHandler(req: Request): ZIO[Any, Nothing, Response] = {
    val checkBoardValidity: ZIO[Any, String, Boolean] = (for {
      bodyStr <- req.body.asString.mapError(_.getMessage)
      parseBodyAsSudokuBoard <- ZIO.fromEither(bodyStr.fromJson[SudokuBoard])
      isValidBoard <- SudokuValidationService.isValidBoard(parseBodyAsSudokuBoard)
    } yield(isValidBoard))

    checkBoardValidity.map { isValidBoard =>
      if (isValidBoard)
        Response.text("""Board is valid!""")
      else
        Response.text("""Board is invalid!""")
    }.catchAll { error =>
      ZIO.succeed(Response.error(
        zio.http.Status.InternalServerError,
        s"""Error while generating board! ERROR: $error"""
      ))
    }

  }

}

