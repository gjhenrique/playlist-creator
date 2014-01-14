import scala.collection.mutable.MutableList

class Playlist {

  private var _musics = MutableList[String]()

  private var _playlists = MutableList[Playlist]()

  val formats = List("mp3", "wav")
  
  def musics : MutableList[String] = _musics

  def playlists : MutableList[Playlist] = _playlists
  
  def addMusic(music : String) : Unit = {
    val musicFormat = music.split("\\.").last
    if(formats.contains(musicFormat))
      musics += music
  }

  def addPlaylist(playlist : Playlist) {
    if(playlist.valid) {
      playlists += playlist
    }
  }

  def valid : Boolean = {
    if(musics.size > 0) return true

    for(playlist <- playlists) {
      val valid = playlist.valid
      if(valid == true)
        return valid
    }
    false
  }
}
