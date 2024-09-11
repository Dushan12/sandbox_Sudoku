import com.sudoku.utils.{BoardGeneratorUtil, PrintUtil}
import zio.*
import zio.http.*

object main extends ZIOAppDefault {

  private val routes: Routes[Any, Nothing] =
    Routes(
      Method.GET / "generate" -> handler(Response.text("Greetings at your service"))
    )



  
  def run: ZIO[ZIOAppArgs & Scope, Any, Any] = Server.serve(routes).provide(Server.default)

}

