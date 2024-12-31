package com.example.btlmusic.Opject;

public class Account {
    private int id;
    private String phone;
    private String name;
    private String pass;
    private String img;

    public Account() {
    }

    public Account(int id, String phone, String name, String pass, String img) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.pass = pass;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
