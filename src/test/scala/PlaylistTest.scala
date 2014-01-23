import org.scalatest.{Matchers, BeforeAndAfter, FlatSpec}

class PlaylistTest extends FlatSpec with BeforeAndAfter with Matchers{

  private var playlist : Playlist = _

  private var invalidPlaylist : Playlist = _
  private var validPlaylist : Playlist = _

  before {
    playlist = new Playlist

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

  it should "return sanitized playlist name" in {
    asssertToSanitizedPlaylistName("01. Pearl Jam", "Pearl Jam")
    asssertToSanitizedPlaylistName("01 Pearl Jam", "Pearl Jam")
    asssertToSanitizedPlaylistName("200", "200")
    asssertToSanitizedPlaylistName("Disc 10 Music", "Disc 10 Music")
    asssertToSanitizedPlaylistName("1991 - Ten", "Ten")
    asssertToSanitizedPlaylistName("Vitalogy (1994)", "Vitalogy")
    asssertToSanitizedPlaylistName("[1994] Vitalogy", "Vitalogy")
    asssertToSanitizedPlaylistName("(1994) Vitalogy", "Vitalogy")
    asssertToSanitizedPlaylistName("02 Vs (1993)", "Vs")
    asssertToSanitizedPlaylistName("05. Yield [1993]", "Yield")
    asssertToSanitizedPlaylistName("Yield - 1993", "Yield")
  }

  def asssertToSanitizedPlaylistName(rawName: String, sanitizedName: String) {
    val playlist = new Playlist(rawName)
    playlist.name shouldBe sanitizedName
  }

}