package com.example.myapplicationthi;

public class SinhVien {

    private  int id ;
    private  String name;
    private  String gioitinh;
    private  String chuyennganh;
    private  double diemtb;
    private  String diachi;

    public SinhVien() {
    }

    public SinhVien(int id, String name, String gioitinh, String chuyennganh, double diemtb, String diachi) {
        this.id = id;
        this.name = name;
        this.gioitinh = gioitinh;
        this.chuyennganh = chuyennganh;
        this.diemtb = diemtb;
        this.diachi = diachi;
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

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getChuyennganh() {
        return chuyennganh;
    }

    public void setChuyennganh(String chuyennganh) {
        this.chuyennganh = chuyennganh;
    }

    public double getDiemtb() {
        return diemtb;
    }

    public void setDiemtb(double diemtb) {
        this.diemtb = diemtb;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    @Override
    public String toString() {
        return name;
    }
}
