package com.example.project.myapplication;

/**
 * Created by Rajkumar on 07-24-2015.
 */
public class UserLoginStatus {

    public static int status =0;   //logged out
    public static String user_name;
    public static int  user_id;
    public static String sharedPreferencesUserLoginDetailsFileName = "user_info";
    public static String sharedPreferencesUserTagName = "user_name";
    public static String sharedPreferencesPasswordTagName = "password";
    public static String sharedPreferencesUserIdTagName = "user_id";

    public UserLoginStatus(String user_name, int status) {
        this.user_name = user_name;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
