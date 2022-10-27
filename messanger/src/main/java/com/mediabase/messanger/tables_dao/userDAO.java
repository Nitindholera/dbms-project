package com.mediabase.messanger.tables_dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.user;

@Repository
public class userDAO {
    private JdbcTemplate jdbcTemplate;

    public userDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<user> fetchall(){
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<user>(user.class));
    }
}
