import scala.collection.mutable.MutableList

class Playlist(_name : String = null ) {

  private val _musics = MutableList[String]()

  private val _playlists = MutableList[Playlist]()

  private val formats = List("mp3", "wav")
  
  def musics : MutableList[String] = {
    var musicsPlaylists = MutableList[String]()

    for(playlist <- playlists)
      musicsPlaylists ++= playlist.musics
    _musics ++ musicsPlaylists
  }

  def name : String = _name

  def playlists : MutableList[Playlist] = _playlists
  
  def addMusic(music : String) : Unit = {
    val musicFormat = music.split("\\.").last
    if(formats.contains(musicFormat))
      _musics += music
  }

  def addPlaylist(playlist : Playlist) {
    if(playlist.valid)
      playlists += playlist
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
