package com.mediabase.messanger.tables_dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.group_data;

@Repository
public class group_dataDAO {
    private JdbcTemplate jdbcTemplate;

    public group_dataDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<group_data> fetchall(){
        String sql = "SELECT * FROM group_data";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<group_data>(group_data.class));
    }
}
