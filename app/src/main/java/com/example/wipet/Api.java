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
    public static final String getOwnCv = HOME + "/getOwnCv";

    public static final String SAVE_PROFILE = HOME + "/saveProfile" ;

    //Directory in server

    public static final String BASE_PHOTO = BASE + "storage/photo";
    public static final String DIR_USER_PHOTO = BASE_PHOTO + "/user_photo/";
    public static final String DIR_DISCUSSION_PHOTO = BASE_PHOTO + "/diskusi/";
    public static final String DIR_ADOPTION_PHOTO = BASE_PHOTO + "/adoption/";

    //discussion
    public static final String CREATE_DISCUSSION = HOME + "/diskusi/create" ;
    public static final String DETAIL_DISCUSSION = HOME + "/diskusi/detail_discussion" ;
    public static final String SHOW_LIST_DISCUSSION = HOME + "/diskusi/show_list" ;


    //Comments
    public static final String CREATE_COMMENT = HOME + "/comment/create" ;
    //Like
    public static final String CREATE_LIKE_DISCUSSION = HOME + "/like/create_diskusi" ;
    public static final String CREATE_LIKE_ADOPTION = HOME + "/like/create_adoption" ;

    //adoption
    public static final String CREATE_ADOTION = HOME + "/adoption/create";
    public static final String SHOW_LIST_ADOPTION = HOME + "/adoption/show_list";
    public static final String SHOW_ITEM_ADOPTION = HOME + "/adoption/show_item";
}
