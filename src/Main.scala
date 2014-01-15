import java.io.File
import scala.collection.mutable.MutableList

object Main {

  def main(args : Array[String]) {
    val path = if(args.length > 0) args(0) else System.getProperty("user.dir")

    val generator = new PlaylistGenerator(path)
    generator.writePlaylistsRecursive

  }
}


