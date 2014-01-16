import org.scalatest.{BeforeAndAfter, FlatSpec}

class Test extends FlatSpec with BeforeAndAfter {

  private var playlist : Playlist = _

  private var invalidPlaylist : Playlist = _
  private var validPlaylist : Playlist = _

  before {
    playlist = new Playlist;

    invalidPlaylist = new Playlist
    validPlaylist = new Playlist
    validPlaylist.addMusic("music.mp3")
  }

  it should "discard non music file" in {
    playlist.addMusic("music.abc")
    assert(playlist.musics.size == 0, "Shouldnt add non music file")
  }

  it should "allow add music files" in {
    playlist.addMusic("music.mp3")
    assert(playlist.musics.size == 1, "Should add music file")
  }

  it should "be valid with musics" in {
    playlist.addMusic("music.mp3")
    assert(playlist.valid)
  }

  it should "not be valid with no musics" in {
    assert(!playlist.valid)
  }

  it should "be valid with invalid folder having valid folder" in {

    invalidPlaylist.addPlaylist(validPlaylist)
    playlist.addPlaylist(invalidPlaylist)

    assert(playlist.playlists.size == 1)
  }

  it should "return the musics of playlists and all children" in {

    playlist.addMusic("music1.mp3")

    val playlist1 = new Playlist
    playlist.addMusic("music2.mp3")

    val playlist2 = new Playlist
    playlist2.addMusic("music3.mp3")

    playlist1.addPlaylist(playlist2)
    playlist.addPlaylist(playlist1)

    assert(playlist.musics.contains("music1.mp3"))
    assert(playlist.musics.contains("music2.mp3"))
    assert(playlist.musics.contains("music3.mp3"))
  }
}