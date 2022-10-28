package com.mediabase.messanger.tables_dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.is_member_friend;

@Repository
public class is_member_friendDAO {
    private JdbcTemplate jdbcTemplate;

    public is_member_friendDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<is_member_friend> fetchall(){
        String sql = "SELECT * FROM is_member_friend";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<is_member_friend>(is_member_friend.class));
    }
}
