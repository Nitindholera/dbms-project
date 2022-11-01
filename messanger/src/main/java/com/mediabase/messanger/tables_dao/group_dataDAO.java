package com.mediabase.messanger.tables_dao;

import java.util.HashMap;
import java.util.List;

import com.mediabase.messanger.tables.is_member_group;
import com.mediabase.messanger.tables.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.group_data;

@Repository
public class group_dataDAO {
    @Autowired
    chatDAO chatDAO;
//    Initialize with maximum group id.
    int g_id = 0;


    private JdbcTemplate jdbcTemplate;

    public group_dataDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<group_data> fetchall(){
        String sql = "SELECT * FROM group_data";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<group_data>(group_data.class));
    }

    public group_data fetchgroup(Integer g_id){
        String sql = "SELECT * FROM group_data " + "where Group_id = \"" + g_id + "\";";
        List<group_data> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(group_data.class));
        if(a.size() == 0) return null;
        return a.get(0);
    }

    public HashMap grp_create(user sender, group_data group){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(group.getName() == null || group.getDescription()==null) map.put("group", "Insufficient data.");
        if(map.size()>0) return map;
        Integer Chat_id = chatDAO.New_Chat();
        String sql = "insert into group_data(Group_id, name, chat_id, description) values("+ g_id + ", \"" + group.getName() + "\","+  Chat_id + ", \"" + group.getDescription() +"\"); ";
        jdbcTemplate.execute(sql);
        sql = "insert into is_member_group(Group_id, User_name, is_admin) values(" + g_id + ", \"" + sender.getUser_name() + "\", 1);";
        jdbcTemplate.execute(sql);
        return map;
    }


    public HashMap grp_exit(user sender, group_data group){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(group.getGroup_id() == null) map.put("group", "Insufficient data.");
        group = fetchgroup(group.getGroup_id());
        if(group.getGroup_id() == null) map.put("group", "Insufficient data.");
        if(map.size()>0) return map;
        
        String sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\" and is_admin = 1";        
        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));

        sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and is_admin = 1";
        List<is_member_group> admin_no = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));

        sql = "select * from is_member_group where Group_id = " + group.getGroup_id();
        List<is_member_group> members_no = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));

        if(admin_no.size() == 1 && x.size()==1 && members_no.size()>1) map.put("group","Admin cant leave the group");
        if(map.size()>0) return map;

        if(members_no.size()==1){
            sql = "delete from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\"";
            jdbcTemplate.execute(sql);
            sql = "delete from group_data where Group_id = " + group.getGroup_id();
            jdbcTemplate.execute(sql);
            sql = "delete from chat where Chat_id = " + group.getChat_id();
            jdbcTemplate.execute(sql);
        }
        else{
            sql = "delete from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\"";
            jdbcTemplate.execute(sql);
        }
        return map;
    }
}
