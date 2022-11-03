package com.mediabase.messanger.tables;

public class is_member_group {
    private Integer Group_id;
    public Integer getGroup_id() {
        return Group_id;
    }
    public void setGroup_id(Integer group_id) {
        Group_id = group_id;
    }
    private String User_name;
    private Boolean is_admin;
    public String getUser_name() {
        return User_name;
    }
    public void setUser_name(String user_name) {
        User_name = user_name;
    }
    public Boolean getIs_admin() {
        return is_admin;
    }
    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }
    public is_member_group(Integer group_id, String user_name, Boolean is_admin) {
        Group_id = group_id;
        User_name = user_name;
        this.is_admin = is_admin;
    }
    public is_member_group(){};
}
