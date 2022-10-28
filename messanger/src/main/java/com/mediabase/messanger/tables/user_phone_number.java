package com.mediabase.messanger.tables;

public class user_phone_number {
    private String phone_number;
    private Integer User_id;

    public user_phone_number(){}

    public user_phone_number(String phone_number, Integer user_id) {
        this.phone_number = phone_number;
        User_id = user_id;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public Integer getUser_id() {
        return User_id;
    }
    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }

}
