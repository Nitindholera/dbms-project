package com.mediabase.messanger.WebSocket;

import java.time.LocalDateTime;

public class Message_class {
    private Integer send_by;
    private LocalDateTime time;
    private String data;
    private Integer Chat_id;

    public Message_class(Integer send_by, LocalDateTime time, String data, Integer chat_id) {
        this.send_by = send_by;
        this.time = time;
        this.data = data;
        Chat_id = chat_id;
    }

    public Message_class(){

    }

    public Integer getSend_by() {
        return send_by;
    }

    public void setSend_by(Integer send_by) {
        this.send_by = send_by;
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

    public Integer getChat_id() {
        return Chat_id;
    }

    public void setChat_id(Integer chat_id) {
        Chat_id = chat_id;
    }
}
