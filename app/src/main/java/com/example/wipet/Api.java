package com.example.wipet;

public class Api {

    public static final String BASE = "http://192.168.1.15:8000/";
    public static final String HOME = BASE + "api";
    public static final String LOGIN = HOME + "/login";
    public static final String REGISTER = HOME + "/register";
    public static final String getProvince = HOME + "/getProvinsi";
    public static final String getKota = HOME + "/getKota";
    public static final String getKecamatan = HOME + "/getKecamatan";
    public static final String getKelurahan = HOME + "/getKelurahan";

    public static final String SAVE_PROFILE = HOME + "/saveProfile" ;
}
