package com.mediabase.messanger.tables_dao;

import java.time.LocalDateTime;
import java.util.*;

import com.mediabase.messanger.forms.get_friend_form;
import com.mediabase.messanger.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class friendDAO {
    @Autowired
    chatDAO chatDAO;

    @Autowired
    messageDAO messageDAO;



    private JdbcTemplate jdbcTemplate;

    public friendDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<friend> fetchall(){
        String sql = "SELECT * FROM friend";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<friend>(friend.class));
    }

    public friend fetchFriend(String user1, String user2){
        String sql = "select a.Friend_id, c.Chat_id\n" +
                "from is_member_friend as a, is_member_friend as b, friend as c\n" +
                "where a.User_name = \"" + user1 + "\" and b.User_name = \""+ user2 + "\" and a.Friend_id = b.Friend_id and c.Friend_id = a.Friend_id";
        List<friend> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(friend.class));
        if (a.size()==0)return null;
        return a.get(0);
    }

    public HashMap fr_request(user sender, user receiver){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(receiver == null) map.put("receiver", "User not found.");
        if(map.size()>0) return map;
        if(Objects.equals(sender.getUser_name(), receiver.getUser_name())) map.put("error", "Invalid request");
        if(map.size()==0) {
            String sql = "SELECT * FROM friend_requests where sender = \"" + sender.getUser_name() + "\" and receiver = \"" + receiver.getUser_name() + "\"";
            List<friend_requests> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(friend_requests.class));
            if(a.size()>0) map.put("error", "Request already sent");
            if(map.size()>0) return map;
            sql = "insert into friend_requests(sender, receiver) values(\"" + sender.getUser_name() + "\", \"" + receiver.getUser_name() + "\")";
            jdbcTemplate.execute(sql);
        }
        return map;
    }

    public HashMap fr_response(user sender, user receiver, Boolean bool){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(receiver == null) map.put("receiver", "User not found.");
        if(bool==null) map.put("bool", "Bool can not be empty");
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
                sql = "select max(Friend_id) as Friend_id from friend";
                List<friend> r = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(friend.class));
                int fd = 0;
                if(r.get(0).getFriend_id() != null) fd = r.get(0).getFriend_id() + 1;
                sql = "insert into friend(Friend_id, Chat_id) values(" + fd + "," + Chat_id + ")";
                jdbcTemplate.execute(sql);

                sql = "insert into is_member_friend(Friend_id, User_name) values(" + fd + ", \"" + sender.getUser_name() + "\")";
                jdbcTemplate.execute(sql);
                sql = "insert into is_member_friend(Friend_id, User_name) values(" + fd + ", \"" + receiver.getUser_name() + "\")";
                jdbcTemplate.execute(sql);
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


    public List<get_friend_form> get_friends(user sender) {
        List<get_friend_form> re = new ArrayList<>();
        String sql = "select friend.Friend_id, friend.Chat_id\n" +
                "from is_member_friend as im, friend\n" +
                "where im.User_name = \""+ sender.getUser_name() +"\" and im.Friend_id = friend.Friend_id;";
        List<friend> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(friend.class));
        List<user> c;
        for (friend friend : a) {
            sql = "select * from user as user1\n" +
                    "where(\n" +
                    "select user.User_name = user1.User_name\n" +
                    "from user, is_member_friend as im\n" +
                    "where im.User_name != \"" + sender.getUser_name() + "\" and im.Friend_id = " + friend.getFriend_id() + " and user.User_name = im.User_name);";
            List<user> b = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(user.class));
            user e = b.get(0);
            get_friend_form f = new get_friend_form();
            f.username = e.getUser_name();
            f.img_url = e.getProfile_pic();
            f.first_name = e.getFname();
            f.last_name = e.getLname();
            f.status = e.getDescription();
            f.room = "fr_" + sender.getUser_name() + "_" + e.getUser_name();
            message m = messageDAO.last_message(friend.getChat_id());
            LocalDateTime d = LocalDateTime.parse("2007-12-03T10:15:30");
            if (m == null) f.last_act = d;
            else f.last_act = m.getTime();
            re.add(f);
        }
        re.sort(Comparator.comparing((get_friend_form a2) -> a2.last_act));
        Collections.reverse(re);
        return re;
    }
}
