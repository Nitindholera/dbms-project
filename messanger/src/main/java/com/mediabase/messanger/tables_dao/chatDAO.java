package com.mediabase.messanger.tables_dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.chat;

@Repository
public class chatDAO {
    private JdbcTemplate jdbcTemplate;

//    Initialize it with maximum of Chat_id + 1
    Integer st = 0;

    public chatDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<chat> fetchall(){
        String sql = "SELECT * FROM chat";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<chat>(chat.class));
    }

    public Integer New_Chat(){
        String sql = "insert into chat(Chat_id) values(" + st + ")";
        st++;
        jdbcTemplate.execute(sql);
        return st - 1;
    }
}
