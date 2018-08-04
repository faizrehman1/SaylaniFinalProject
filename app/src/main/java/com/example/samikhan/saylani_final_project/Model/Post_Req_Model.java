package com.example.samikhan.saylani_final_project.Model;

import java.io.Serializable;

/**
 * Created by Sami Khan on 2/26/2017.
 */

public class Post_Req_Model implements Serializable {

    private String blood_Group;
    private int no_of_unit;
    private String Urgency;
    private String  country;
    private String state;
    private String city;
    private String hospital;
    private  String relation;
    private String contact_no;
    private String instruction;
    private int volunteer;
    private String user_name;
    private String post_id;

    public Post_Req_Model() {
    }

    public Post_Req_Model(String blood_Group, int no_of_unit, String urgency, String country, String state, String city, String hospital, String relation, String contact_no, String instruction, int volunteer, String user_name, String post_id) {
        this.blood_Group = blood_Group;
        this.no_of_unit = no_of_unit;
        Urgency = urgency;
        this.country = country;
        this.state = state;
        this.city = city;
        this.hospital = hospital;
        this.relation = relation;
        this.contact_no = contact_no;
        this.instruction = instruction;
        this.volunteer = volunteer;
        this.user_name = user_name;
        this.post_id = post_id;
    }



    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(int volunteer) {
        this.volunteer = volunteer;
    }

    public String getBlood_Group() {
        return blood_Group;
    }

    public void setBlood_Group(String blood_Group) {
        this.blood_Group = blood_Group;
    }

    public int getNo_of_unit() {
        return no_of_unit;
    }

    public void setNo_of_unit(int no_of_unit) {
        this.no_of_unit = no_of_unit;
    }

    public String getUrgency() {
        return Urgency;
    }

    public void setUrgency(String urgency) {
        Urgency = urgency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
