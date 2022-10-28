package com.mediabase.messanger.tables_dao;

import java.util.List;

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
}
