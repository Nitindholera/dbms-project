package com.mediabase.messanger.tables;

import java.sql.Time;

public class message {
    private Integer message_id;
    private Integer send_by;
    private Time time;
    private String data;
    private Integer Chat_id;
    public Integer getMessage_id() {
        return message_id;
    }
    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }
    public Integer getSend_by() {
        return send_by;
    }
    public void setSend_by(Integer send_by) {
        this.send_by = send_by;
    }
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
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
    public message(){}
    public message(Integer message_id, Integer send_by, Time time, String data, Integer chat_id) {
        this.message_id = message_id;
        this.send_by = send_by;
        this.time = time;
        this.data = data;
        Chat_id = chat_id;
    }

}
