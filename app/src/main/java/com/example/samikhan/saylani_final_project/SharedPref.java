package com.example.samikhan.saylani_final_project;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.samikhan.saylani_final_project.Model.UserModels;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class SharedPref  {

    private static String NAME = "packageName";
    private static String U_FNAME = "fname";
    private static String U_LNAME = "lanme";
    private static String U_ID = "userid";
    private static String U_EMAIL = "email";
    private static String U_BLOOD_GROUP = "bloodgroup";
    private static String U_DOB = "u_dob";
    private static String U_GEND = "u_gea";
    private static String U_STATUS = "u_status";

    public static void setCurrentUser(Context context, UserModels user) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(U_FNAME, user.getFname()).apply();
        preferences.edit().putString(U_LNAME, user.getLname()).apply();
        preferences.edit().putString(U_ID, user.getUserID()).apply();
        preferences.edit().putString(U_EMAIL, user.getEmail()).apply();
        preferences.edit().putString(U_BLOOD_GROUP,user.getBlood_group()).apply();
    }

    public static UserModels getCurrentUser(Context context) {
        UserModels user = new UserModels();
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        user.setFname(preferences.getString(U_FNAME, ""));
        user.setLname(preferences.getString(U_LNAME, ""));
        user.setEmail(preferences.getString(U_EMAIL, ""));
        user.setUserID(preferences.getString(U_ID, ""));
        user.setBlood_group(preferences.getString(U_BLOOD_GROUP,""));
        return user;
    }


}

