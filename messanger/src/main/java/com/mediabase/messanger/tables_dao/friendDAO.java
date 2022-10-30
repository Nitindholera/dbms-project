package com.mediabase.messanger.tables_dao;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.mediabase.messanger.tables.friend_requests;
import com.mediabase.messanger.tables.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.tables.friend;

@Repository
public class friendDAO {
    @Autowired
    chatDAO chatDAO;

//    Initilize it with maximum + 1 friend_id in database
    int fd = 0;


    private JdbcTemplate jdbcTemplate;

    public friendDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<friend> fetchall(){
        String sql = "SELECT * FROM friend";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<friend>(friend.class));
    }

    public HashMap fr_request(user sender, user receiver){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(receiver == null) map.put("receiver", "User not found.");
        if(map.size()>0) return map;
        if(Objects.equals(sender.getUser_name(), receiver.getUser_name())) map.put("error", "Invalid request");
        if(map.size()==0) {
            String sql = "insert into friend_requests(sender, receiver) values(\"" + sender.getUser_name() + "\", \"" + receiver.getUser_name() + "\")";
            jdbcTemplate.execute(sql);
        }
        return map;
    }

    public HashMap fr_response(user sender, user receiver, Boolean bool){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(receiver == null) map.put("receiver", "User not found.");
        if(map.size()>0) return map;
        if(Objects.equals(sender.getUser_name(), receiver.getUser_name())) map.put("error", "Invalid request");
        String sql = "SELECT * FROM friend_requests where sender = \"" + sender.getUser_name() + "\" and receiver = \"" + receiver.getUser_name() + "\"";
        List<friend_requests> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(friend_requests.class));
        if(a.size()==0) map.put("error", "Invalid request");
        if(map.size()==0) {
            sql = "DELETE FROM friend_requests where sender = \"" + sender.getUser_name() + "\" and receiver = \"" + receiver.getUser_name() + "\"";
            jdbcTemplate.execute(sql);
            if(bool) {
                Integer Chat_id = chatDAO.New_Chat();
                sql = "insert into friend(Friend_id, Chat_id) values(" + fd + "," + Chat_id + ")";
                jdbcTemplate.execute(sql);

                sql = "insert into is_member_friend(Friend_id, User_name) values(" + fd + ", \"" + sender.getUser_name() + "\")";
                jdbcTemplate.execute(sql);
                sql = "insert into is_member_friend(Friend_id, User_name) values(" + fd + ", \"" + receiver.getUser_name() + "\")";
                jdbcTemplate.execute(sql);
                fd++;
            }
        }
        return map;
    }

    public HashMap fr_remove(user sender, user receiver){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "User not found.");
        if(receiver == null) map.put("receiver", "Unauthorized access.");
        if(map.size()>0) return map;
        if(Objects.equals(sender.getUser_name(), receiver.getUser_name())) map.put("error", "Invalid request");
        String sql = "select a.Friend_id, c.Chat_id\n" +
                "from is_member_friend as a, is_member_friend as b, friend as c\n" +
                "where a.User_name = \"" + sender.getUser_name() + "\" and b.User_name = \""+ receiver.getUser_name() + "\" and a.Friend_id = b.Friend_id and c.Friend_id = a.Friend_id";
        List<friend> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(friend.class));
        if(a.size()==0) map.put("error", "Invalid request");
        if(map.size()==0) {
            Integer f_id = a.get(0).getFriend_id();
            Integer Chat_id = a.get(0).getChat_id();
            sql = "DELETE FROM is_member_friend where Friend_id = " + f_id + " and User_name = \"" + sender.getUser_name() + "\"";
            jdbcTemplate.execute(sql);

            sql = "DELETE FROM is_member_friend where Friend_id = " + f_id + " and User_name = \"" + receiver.getUser_name() + "\"";
            jdbcTemplate.execute(sql);

            sql = "DELETE FROM friend where Friend_id = " + f_id;
            jdbcTemplate.execute(sql);

            sql = "DELETE FROM chat where Chat_id = " + Chat_id;
            jdbcTemplate.execute(sql);
        }
        return map;
    }
}
