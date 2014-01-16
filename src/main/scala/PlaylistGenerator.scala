import java.io.{PrintWriter, File}

class PlaylistGenerator(_path : String) {

  val path = new File(_path).getAbsolutePath

  val PLAYLIST_FOLDER_NAME = "Playlists"

  val PLAYLIST_SEPARATOR = " - "

  def createPlaylist : Playlist = {
    initializePlaylistFolder

    val file = new File(path)
    createPlaylist(file.listFiles(), null)
  }

  def initializePlaylistFolder() {
    val playlistFolder = new File(path + File.separator + PLAYLIST_FOLDER_NAME)

    if (!playlistFolder.exists())
      playlistFolder.mkdir()
    else
      playlistFolder.listFiles().foreach(f => f.delete)
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

    writeMusicsToFile(playlist, path.split("/").last)

    writePlaylistsRecursive(playlist)
  }

  def writePlaylistsRecursive(playlist : Playlist, name : String = "") {

    for(playlist <- playlist.playlists) {
      val playlistName = if(name == "" || name == null) playlist.name else name +  PLAYLIST_SEPARATOR + playlist.name

      writeMusicsToFile(playlist, playlistName)

      writePlaylistsRecursive(playlist, playlistName)
    }
  }

  private def writeMusicsToFile( playlist : Playlist, playlistName : String) {

    val playlistFile = new File(path + File.separator + PLAYLIST_FOLDER_NAME + File.separator + playlistName + ".m3u")

    val writer = new PrintWriter(playlistFile)
    for(music <- playlist.musics) {
      writer.println(music)
    }
    writer.close()
  }

}
