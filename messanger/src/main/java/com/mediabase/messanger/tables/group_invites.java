package com.mediabase.messanger.tables;

public class group_invites {
    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public Integer getGroup_id() {
        return Group_id;
    }

    public void setGroup_id(Integer group_id) {
        Group_id = group_id;
    }

    private String User_name;
    private Integer Group_id;
}
