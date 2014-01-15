import java.io.{File, FileFilter}
import org.scalatest.{Matchers, Ignore, FlatSpec, BeforeAndAfter}
import scala.io.Source

class PlaylistGeneratorTest extends FlatSpec with BeforeAndAfter with Matchers{

  val generator = new PlaylistGenerator("test_folder")

  before {
    val testFile = new File("test_folder")
    testFile
      .listFiles()
      .toList
      .filter(_.getName().endsWith(".m3u"))
      .foreach(_.delete())
  }

  it should "have playlist with Playlist" in {
    val playlist = generator.createPlaylist

    assert(musicsOfPlaylistEndsWith(playlist, "music1.mp3"))
    assert(musicsOfPlaylistEndsWith(playlist, "Dirty Frank.mp3"))
    assert(musicsOfPlaylistEndsWith(playlist, "Given To Fly.mp3"))
    assert(!musicsOfPlaylistEndsWith(playlist, "folder.jpg"))

  }

  def musicsOfPlaylistEndsWith(playlist : Playlist, fileName : String) : Boolean = {
    for(music <- playlist.musics){
      if(music.endsWith(fileName)) return true
    }
    false
  }

  it should "have children playlists" in {
    val generator = new PlaylistGenerator("test_folder/Pearl Jam")
    val playlist = generator.createPlaylist
    assert(playlist.playlists.size == 2)
//    assert(playlist.musics.contains("Given To Fly.mp3"))
  }

  it should "write playlist file to root path" in {
    generator.writePlaylistsRecursive()

    val lines = Source.fromFile("test_folder/Pearl Jam.m3u").mkString
    lines should include("/test_folder/Pearl Jam/Yield/Given To Fly.mp3")
  }

  it should "create children playlists with appropriate separator" in {
    generator.writePlaylistsRecursive()

    val lines = Source.fromFile("test_folder/Pearl Jam - Ten.m3u").mkString
    lines should include("/test_folder/Pearl Jam/Ten/Dirty Frank.mp3")
  }
  it should "not include folder with non music files" in {
    generator.writePlaylistsRecursive()

    assert(!(new File("test_folder/Pearl Jam - Info.m3u").exists()))
  }
}
