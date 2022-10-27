package com.mediabase.messanger.tables;

import java.util.Date;

public class user {
    private Integer User_id;
    private String Fname;
    private String Mname;
    private String Lname;
    private String Description;
    private String Email_id;
    private String Password;
    private String Profile_pic;
    private Date Date_of_birth;
    private Character Gender;
    private Boolean is_verified;
    private Integer verify_pin;

    public user(Integer user_id, String fname, String mname, String lname, String description, String email_id,
            String password, String profile_pic, Date date_of_birth, Character gender, Boolean is_verified,
            Integer verify_pin) {
        User_id = user_id;
        Fname = fname;
        Mname = mname;
        Lname = lname;
        Description = description;
        Email_id = email_id;
        Password = password;
        Profile_pic = profile_pic;
        Date_of_birth = date_of_birth;
        Gender = gender;
        this.is_verified = is_verified;
        this.verify_pin = verify_pin;
    }

    public Date getDate_of_birth() {
        return Date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        Date_of_birth = date_of_birth;
    }

    public user(){}


    
    public Integer getUser_id() {
        return User_id;
    }
    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }
    public String getFname() {
        return Fname;
    }
    public void setFname(String fname) {
        Fname = fname;
    }
    public String getMname() {
        return Mname;
    }
    public void setMname(String mname) {
        Mname = mname;
    }
    public String getLname() {
        return Lname;
    }
    public void setLname(String lname) {
        Lname = lname;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public String getEmail_id() {
        return Email_id;
    }
    public void setEmail_id(String email_id) {
        Email_id = email_id;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getProfile_pic() {
        return Profile_pic;
    }
    public void setProfile_pic(String profile_pic) {
        Profile_pic = profile_pic;
    }
    public Character getGender() {
        return Gender;
    }
    public void setGender(Character gender) {
        Gender = gender;
    }
    public Boolean getIs_verified() {
        return is_verified;
    }
    public void setIs_verified(Boolean is_verified) {
        this.is_verified = is_verified;
    }
    public Integer getVerify_pin() {
        return verify_pin;
    }
    public void setVerify_pin(Integer verify_pin) {
        this.verify_pin = verify_pin;
    }

    
}
