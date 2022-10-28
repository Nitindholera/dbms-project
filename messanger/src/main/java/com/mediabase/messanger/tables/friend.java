package com.mediabase.messanger.tables;

public class friend {
    private Integer Friend_id;
    private Integer Chat_id;
    public Integer getFriend_id() {
        return Friend_id;
    }
    public void setFriend_id(Integer friend_id) {
        Friend_id = friend_id;
    }
    public Integer getChat_id() {
        return Chat_id;
    }
    public void setChat_id(Integer chat_id) {
        Chat_id = chat_id;
    }
    public friend(Integer friend_id, Integer chat_id) {
        Friend_id = friend_id;
        Chat_id = chat_id;
    }
    public friend(){}
}
