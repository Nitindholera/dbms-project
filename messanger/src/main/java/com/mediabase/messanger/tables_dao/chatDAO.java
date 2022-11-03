package com.mediabase.messanger.tables_dao;

import java.util.List;

import com.mediabase.messanger.tables.message;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.chat;

@Repository
public class chatDAO {
    private JdbcTemplate jdbcTemplate;


    public chatDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<chat> fetchall(){
        String sql = "SELECT * FROM chat";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<chat>(chat.class));
    }

    public Integer New_Chat(){
        String sql = "select max(chat_id) as Chat_id from chat";
        List<chat> a = jdbcTemplate.query(sql,new BeanPropertyRowMapper<chat>(chat.class));
        int st = 0;
        if(a.get(0).getChat_id()!=null)  st = a.get(0).getChat_id() + 1;
        sql = "insert into chat(Chat_id) values(" + st + ")";
        jdbcTemplate.execute(sql);
        return st - 1;
    }

    public chat fetchChat(Integer Chat_id){
        String sql = "SELECT * FROM chat where Chat_id = " + Chat_id;
        List<chat> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(chat.class));
        if (a.size()==0)return null;
        return a.get(0);
    }


    public List<message> retrieve_message(chat ch, Integer index) {
        String sql = "SELECT * FROM message where Chat_id = " + ch.getChat_id();
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(message.class));
    }

//    public
}
