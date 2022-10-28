package com.mediabase.messanger.tables_dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.friend;

@Repository
public class friendDAO {
    private JdbcTemplate jdbcTemplate;

    public friendDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<friend> fetchall(){
        String sql = "SELECT * FROM friend";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<friend>(friend.class));
    }
}
