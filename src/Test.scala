import org.scalatest.{BeforeAndAfter, FlatSpec}

class Test extends FlatSpec with BeforeAndAfter {

  behavior of "PlayList"
  private var playlist: Playlist = new Playlist;

  it should "should discard non music file" in {
    playlist.addMusic("music.mp2")
    assert(playlist.musics.size == 0, "Não deveria adicionar "
  }
}