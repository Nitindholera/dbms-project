package com.mediabase.messanger.tables;

public class group_data {
    private Integer Group_id;
    private String name;
    private String description;
    private String picture;
    private Integer chat_id;

    public group_data(Integer group_id, String name, String description, String picture, Integer chat_id) {
        Group_id = group_id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.chat_id = chat_id;
    }
    public group_data(){}
    public Integer getGroup_id() {
        return Group_id;
    }
    public void setGroup_id(Integer group_id) {
        Group_id = group_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public Integer getChat_id() {
        return chat_id;
    }
    public void setChat_id(Integer chat_id) {
        this.chat_id = chat_id;
    }


}
