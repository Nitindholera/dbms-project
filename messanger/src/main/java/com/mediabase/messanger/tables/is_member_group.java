package com.mediabase.messanger.tables;

public class is_member_group {
    private Integer Group_id;
    private Integer User_id;
    private Boolean is_admin;
    public is_member_group(Integer group_id, Integer user_id, Boolean is_admin) {
        Group_id = group_id;
        User_id = user_id;
        this.is_admin = is_admin;
    }
    public is_member_group(){}
    public Integer getGroup_id() {
        return Group_id;
    }
    public void setGroup_id(Integer group_id) {
        Group_id = group_id;
    }
    public Integer getUser_id() {
        return User_id;
    }
    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }
    public Boolean getIs_admin() {
        return is_admin;
    }
    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

}
