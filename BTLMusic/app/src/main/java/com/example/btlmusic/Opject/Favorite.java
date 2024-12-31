package com.example.btlmusic.Opject;

public class Favorite {
    private int id_account;
    private int id_song;

    public Favorite() {
    }

    public Favorite(int id_account, int id_song) {
        this.id_account = id_account;
        this.id_song = id_song;
    }

    public int getId_song() {
        return id_song;
    }

    public void setId_song(int id_song) {
        this.id_song = id_song;
    }

    public int getId_account() {
        return id_account;
    }

    public void setId_account(int id_account) {
        this.id_account = id_account;
    }
}
