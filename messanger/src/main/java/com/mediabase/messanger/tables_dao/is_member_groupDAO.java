package com.mediabase.messanger.tables_dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.is_member_group;

@Repository
public class is_member_groupDAO {
    private JdbcTemplate jdbcTemplate;

    public is_member_groupDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<is_member_group> fetchall(){
        String sql = "SELECT * FROM user_phone_number";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
    }
}
