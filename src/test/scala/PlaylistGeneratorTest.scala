import java.io.{File, FileFilter}
import org.scalatest.{Matchers, Ignore, FlatSpec, BeforeAndAfter}
import scala.io.{BufferedSource, Source}

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
    assert(playlist.playlists.size == 3)
  }

  it should "write playlist file to root path" in {
    generator.writePlaylistsRecursive()

    val lines = getPlaylistContent(List("Pearl Jam"))
    lines should include("/test_folder/Pearl Jam/Yield/Given To Fly.mp3")
  }

  it should "create children playlists with musics" in {
    generator.writePlaylistsRecursive()

    val lines = getPlaylistContent(List("Pearl Jam", "Ten"))
    lines should include("/test_folder/Pearl Jam/Ten/Dirty Frank.mp3")
  }

  it should "create greatchildren playlists with musics" in {
    generator.writePlaylistsRecursive();
    val lines = getPlaylistContent(List("Pearl Jam", "Rearviewmirror", "CD 1"))
    lines should include("/test_folder/Pearl Jam/Rearviewmirror/CD 1/Alive.mp3")
  }

  it should "create playlist with the root playlist" in {
    generator.writePlaylistsRecursive()
    val lines = getPlaylistContent(List("test_folder"))
    lines should include("music1.mp3")
    lines should include("/test_folder/Pearl Jam/Rearviewmirror/CD 1/Alive.mp3")
  }

  it should "not include folder with non music files" in {
    generator.writePlaylistsRecursive()

    assert(!(new File("test_folder/Pearl Jam - Info.m3u").exists()))
  }

  it should "create playlist with sanitized name" in {
    generator.writePlaylistsRecursive()
    assert(!(new File("test_folder/Pearl Jam - Yield.m3u").exists()))
  }
  def getPlaylistContent(files : List[String]) : String = {
    val playlistName = files.mkString(generator.PLAYLIST_SEPARATOR)
    val path = "test_folder/" + generator.PLAYLIST_FOLDER_NAME + "/" + playlistName + ".m3u"
    Source.fromFile(path).mkString
  }
}
