package com.mediabase.messanger.tables;

public class is_member_friend {
    private Integer Friend_id;
    private Integer User_id;
    public Integer getFriend_id() {
        return Friend_id;
    }
    public void setFriend_id(Integer friend_id) {
        Friend_id = friend_id;
    }
    public Integer getUser_id() {
        return User_id;
    }
    public void setUser_id(Integer user_id) {
        User_id = user_id;
    }
    public is_member_friend(Integer friend_id, Integer user_id) {
        Friend_id = friend_id;
        User_id = user_id;
    }
    public is_member_friend(){}

}
