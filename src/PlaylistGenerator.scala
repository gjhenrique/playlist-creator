import java.io.{PrintWriter, File}

class PlaylistGenerator(_path : String) {

  val path = new File(_path).getAbsolutePath

  def createPlaylist : Playlist = {
    val file = new File(path)
    createPlaylist(file.listFiles(), null)
  }

  def createPlaylist(files : Array[File], name : String) : Playlist = {
    val playlist = new Playlist(name)

    for (file <- files) {
      if (file.isDirectory) {
        val playlistChild = createPlaylist(file.listFiles(), file.getName)
        playlist.addPlaylist(playlistChild)
      } else {
        playlist.addMusic(file.getAbsolutePath)
      }
    }
    return playlist
  }

  def writePlaylistsRecursive() {
    val playlist = createPlaylist
    writePlaylistsRecursive(playlist)
  }

  def writePlaylistsRecursive(playlist : Playlist, name : String = "") {

    for(playlist <- playlist.playlists) {

      val playlistName = if(name == "" || name == null) playlist.name else name + " - " + playlist.name

      val playlistFile = new File(path + File.separator + playlistName + ".m3u")
//      val playlistFile = new File(playlistName + ".m3u")
      val writer = new PrintWriter(playlistFile)

      for(music <- playlist.musics) {
        writer.println(music)
      }

      writer.close()

      writePlaylistsRecursive(playlist, playlist.name)
    }
  }


}
