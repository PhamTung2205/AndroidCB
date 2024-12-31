package com.example.btlmusic.Opject;

public class Album {
    private int id;
    private String name;
    private String img;
    private int id_artist;

    public Album() {
    }

    public Album(int id, String name, String img, int id_artist) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.id_artist = id_artist;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }
    @Override
    public String toString() {
        return name;
    }
}

