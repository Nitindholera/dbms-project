package com.mediabase.messanger.tables_dao;

import java.util.List;

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
}
