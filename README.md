# Disclaimer
This code was developed a long time ago and I don't use it anymore.
[Mopidy](https://www.mopidy.com/) connected with Spotify deprecated this tool.

Also, I know that one line of bash would solve this problem, but this tool was made for educational purposes. =)

# Build
Generate the jar inside target/scala-2.10/playlist-creator-assembly-0.1.jar
```bash
sbt assembly
```

# What is this
This tool builds several playlist files (.m3u files) recursively from a folder of musics.

A .m3u file contains all the musics with the following format:
```
<path of the music>/Music1.mp3
<path of the music>/Music2.mp3
<path of the music>/Music3.mp3
```

This type of file is simple and can be opened by all the music players.

An ASCII image is worth a thousand words, so let's show this with an example.
In this example, we have a folder Music with the following songs:
```
Music
├── Pearl Jam
│   ├── Ten
│   │   ├── Once.mp3
│   │   ├── Even flow.mp3
│   ├── Vs
│   │   ├── Go.mp3
│   │   ├── Animal.mp3
├── Blink 182
│   ├── Dude Ranch
│   │   ├── Pathetic.mp3
│   │   ├── Voyeur.mp3
├── The White Stripes
│   ├── Jimmy the Exploder.mp3
│   ├── Stop Breaking Down.mp3
```

Run the command inside the musics folder. 
```
java -jar <path of the jar>/playlist-creator-assembly-0.1.jar
```
A ```Playlist``` directory is created with the these files:
```
Blink 182.m3u
Blink 182 - Dude Ranch.m3u
Music.m3u
Pearl Jam.m3u
Pearl Jam - Ten.m3u
Pearl Jam - Versus.m3u
The White Stripes.m3u
```
* ```Music.m3u``` has all the musics of the ```Music``` directory.
* ```Pearl Jam.m3u``` contains the Pearl Jam's musics 
* ```Pearl Jam - Ten.m3u``` contains all the musics from the Pearl Jam's Ten album

