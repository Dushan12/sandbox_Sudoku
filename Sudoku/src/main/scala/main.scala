import com.sudoku.services.ValidationService
import com.sudoku.utils.{BoardGeneratorUtil, JsonUtils, PrintUtil}
import zio.*
import zio.http.*

object main extends ZIOAppDefault {

  private val routes: Routes[Any, Nothing] =
    Routes(
      Method.GET / "generate" -> handler(Response.text("Greetings at your service")),
      Method.POST / "validate" -> handler { (req: Request) =>
        (for {
          bodyStr <- req.body.asString.orElse(ZIO.succeed(""))
          parseBodyAsSudokuBoard <- JsonUtils.fromJson(bodyStr)
          isValid <- ValidationService.isValid(parseBodyAsSudokuBoard)
        } yield {
          if (isValid)
            Response.text("""Board is valid!""")
          else
            Response.text("""Board is invalid!""")
        }).catchAll { _ =>
          ZIO.succeed(Response.text(s"""Error while generating board!"""))
        }

      }
    )

  def run: ZIO[ZIOAppArgs & Scope, Any, Any] = Server.serve(routes).provide(Server.default)

}

