import com.sudoku.services.ValidationService
import com.sudoku.utils.{BoardGeneratorUtil, JsonUtils, PrintUtil}
import zio.*
import zio.http.*

object main extends ZIOAppDefault {

  private val routes: Routes[Any, Nothing] =
    Routes(
      Method.GET / "greet" -> handler { (req: Request) => greet(req) },
      Method.POST / "validate" -> handler { (req: Request) => validateRequestHandler(req) }
    )

  def run: ZIO[ZIOAppArgs & Scope, Any, Any] = Server.serve(routes).provide(Server.default)

  private def greet(req: Request) = Response.text("Greetings at your service")

  private def validateRequestHandler(req: Request): ZIO[Any, Nothing, Response] = {
    val checkBoardValidity: ZIO[Any, String, Boolean] = (for {
      bodyStr <- req.body.asString.mapError(_.getMessage)
      parseBodyAsSudokuBoard <- JsonUtils.fromJson(bodyStr)
      isValidBoard <- ValidationService.isValidBoard(parseBodyAsSudokuBoard)
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

