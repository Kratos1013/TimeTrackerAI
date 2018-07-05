package com.krintos.timetrackerai.Helper;

/**
 * Created by root on 11/15/17.
 */

public class AppConfig {
    // Server user login url
    public static String IP = "http://89.223.90.102:8095";
    //public static String IP = "http://192.168.137.1:8095";
    public static String URL_LOGIN = IP+"/login";

    public static String URL_SIGN_UP = IP+"/signUp";
    public static String URL_GETUSER = IP+"/user/get";
    public static String URL_GETUSERPHOTO= IP+"/user/photo/";
    public static String URL_UPDATE_USER = IP+"/user/updateUser";



}
