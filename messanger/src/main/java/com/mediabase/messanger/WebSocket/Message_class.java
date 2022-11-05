package com.mediabase.messanger.WebSocket;

import java.time.LocalDateTime;

public class Message_class {

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Message_class(String data) {
        this.data = data;
    }

    public Message_class() {
    }

    public Message_class(String sender, LocalDateTime time, String data, String room) {
        this.sender = sender;
        this.time = time;
        this.data = data;
        this.room = room;
    }

    private String sender;
    private LocalDateTime time;
    private String data;
    private String room;

}
