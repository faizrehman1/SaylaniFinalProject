package com.example.samikhan.saylani_final_project.Model;

/**
 * Created by Sami Khan on 2/24/2017.
 */

public class UserModels {
    private String email;
    private String password;
    private String cpassword;
    private String userID;
    private String fname;
    private String lname;
    private String blood_group;

    public UserModels() {
    }

    public UserModels(String email, String password, String cpassword, String userID, String fname, String lname, String blood_group) {
        this.email = email;
        this.password = password;
        this.cpassword = cpassword;
        this.userID = userID;
        this.fname = fname;
        this.lname = lname;
        this.blood_group = blood_group;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
