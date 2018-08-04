package com.example.samikhan.saylani_final_project.Model;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class Volunteer_model  {
    private String user_email;
    private String blood_group;
    private boolean isdonated;
    private String disc;

    public Volunteer_model() {
    }

    public Volunteer_model(String user_email, String blood_group, boolean isdonated, String disc) {
        this.user_email = user_email;
        this.blood_group = blood_group;
        this.isdonated = isdonated;
        this.disc = disc;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public boolean isdonated() {
        return isdonated;
    }

    public void setIsdonated(boolean isdonated) {
        this.isdonated = isdonated;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }
}
