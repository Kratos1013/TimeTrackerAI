package com.krintos.timetrackerai.Helper;

/**
 * Created by root on 11/15/17.
 */

public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://192.168.137.1:8095/login";
    public static String URL_SIGN_UP = "http://192.168.137.1:8095/signUp";
    //social update link
    public static String URL_SOCIAL_UPDATE = "http://89.223.25.49:8095/updateUserSocialNetwork";

    //pin
    public static String URL_PIN="http://89.223.25.49:8095/getUser";

    public static String URL_UPDATE_NAME = "http://89.223.25.49:8095/updateUser";
    //update work
    public static String URL_UPDATE_WORK = "http://89.223.25.49:8095/updateUserWork";

    public static String URL_REFRESH = "http://89.223.25.49:8095/getUser/";

    // Server user register url
    public static String URL_REGISTER = "http://89.223.25.49:8095/signUp";

    // Server connection ID request
    public static String URL_CONNECTION = "https://couples.000webhostapp.com/connection.php";

    //Server connection to send profile picture
    public static String URL_UPLOAD_PP = "http://89.223.25.49:8095/updateUserImage";



    //server connection to send friend request
    public static String URL_Friend_Req = "https://couples.000webhostapp.com/friendreq.php";

    //link to profileimage
    public static String URL_PROFILE_PIC= "http://89.223.25.49:8095/getUserImage/";


}
