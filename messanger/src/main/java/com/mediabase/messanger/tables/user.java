package com.mediabase.messanger.tables;

import java.time.LocalDate;
import java.util.Date;

public class user {
    private String User_name;
    private String Fname;
    private String Lname;
    private String Description;
    private String Email_id;
    private String Password;
    private String Profile_pic;
    private LocalDate Date_of_birth;
    private Character Gender;
    private Boolean is_verified;
    private Integer verify_pin;

    private String token;

    public user(String user_name, String first_name, String last_name, String description, String email,
                String password, String profile_pic, LocalDate dob, Character gender, Boolean is_verified,
                Integer verify_pin, String token) {
        User_name = user_name;
        Fname = first_name;
        Lname = last_name;
        Description = description;
        Email_id = email;
        Password = password;
        Profile_pic = profile_pic;
        Date_of_birth = dob;
        Gender = gender;
        this.is_verified = is_verified;
        this.verify_pin = verify_pin;
        this.token = token;
    }

    public LocalDate getDate_of_birth() {
        return Date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        Date_of_birth = date_of_birth;
    }

    public user(){}



    public String getUser_name() {
        return User_name;
    }
    public void setUser_name(String user_name) {
        User_name = user_name;
    }
    public String getFname() {
        return Fname;
    }
    public void setFname(String fname) {
        Fname = fname;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
