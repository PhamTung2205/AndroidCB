package com.example.btlmusic.Opject;

public class Song {
    private int id;
    private String name;
    private String type;
    private int view;
    private String img;
    private int idAlbum;


    public Song() {
    }

    public Song(int id, String name, String type, int view, String img,int idAlbum) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.view = view;
        this.img = img;
        this.idAlbum=idAlbum;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }
    @Override
    public String toString() {
        String[] newname = name.split("_");
        String fullname="";
        for(int i=0;i<newname.length;i++){
            fullname +=newname[i].toUpperCase()+" ";
        }

        return fullname;
    }
}

