package com.example.wipet.object;

public class Village {
    private int  id_kecamatan;
    private  long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_kecamatan() {
        return id_kecamatan;
    }

    public void setId_kecamatan(int id_kecamatan) {
        this.id_kecamatan = id_kecamatan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
