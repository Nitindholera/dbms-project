package com.mediabase.messanger.tables_dao;

import java.util.List;

import com.mediabase.messanger.WebSocket.Message_class;
import com.mediabase.messanger.tables.friend;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.message;

@Repository
public class messageDAO {
    private JdbcTemplate jdbcTemplate;

    public messageDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<message> fetchall(){
        String sql = "SELECT * FROM user_phone_number";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<message>(message.class));
    }

    public message last_message(Integer Chat_id)
    {
        String sql="SELECT m.message_id, m.time from message as m where m.Chat_id=Chat_id AND m.time = (select MAX(a.time) from message as a where a.Chat_id=Chat_id)";
            List<message> a =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(message.class));
        if(a.size()==0) return null;
        return a.get(0);
    }

    public void new_message(Integer Chat_id, Message_class m){
        int m_id = 0;
        String sql = "select max(message_id) as message_id from message";
        List<message> r = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(message.class));
        if(r.get(0).getMessage_id() != null) m_id = r.get(0).getMessage_id() + 1;
        sql = "insert into message(message_id, send_by, data, Chat_id) values(" + m_id + ", \"" + m.getSender() + "\", \"" + m.getData() + "\", " + Chat_id + ");";
        jdbcTemplate.execute(sql);
    }
}
