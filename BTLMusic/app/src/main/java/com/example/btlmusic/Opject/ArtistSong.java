package com.example.btlmusic.Opject;

public class ArtistSong {

    private int idSong;
    private int idArtist;

    public ArtistSong() {
    }

    public ArtistSong(int idSong, int idArtist) {
        this.idSong = idSong;
        this.idArtist = idArtist;
    }

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }
}

