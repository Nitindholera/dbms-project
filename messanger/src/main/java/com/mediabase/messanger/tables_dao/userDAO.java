package com.mediabase.messanger.tables_dao;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mediabase.messanger.forms.profile_image_update_form;
import com.mediabase.messanger.tables.group_data;
import com.mediabase.messanger.tables.user;

@Repository
public class userDAO {
    private JdbcTemplate jdbcTemplate;

    public userDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<user> fetchall(){
        String sql = "SELECT * FROM user;";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<user>(user.class));
    }

    public user fetchuser(String user_name){
        String sql = "SELECT * FROM user " + "where User_name = \"" + user_name + "\";";
        List<user> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<user>(user.class));
        if(a.size() == 0) return null;
        return a.get(0);
    }

    public user fetchuser_token(String token){
        String sql = "SELECT * FROM user " + "where token = \"" + token + "\";";
        List<user> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<user>(user.class));
        if(a.size() == 0) return null;
        return a.get(0);
    }

    public HashMap Register(user user){
        HashMap<String, String> map = new HashMap<>();
        if(user.getUser_name() == null || user.getUser_name().length()==0) map.put("user_name", "Username can not be empty.");
        else if (fetchuser(user.getUser_name()) != null) map.put("user_name", "Username already in use.");
        if(user.getEmail_id() == null || user.getEmail_id().length()==0) map.put("email_id", "Email inappropriate or already in use.");
        if(user.getDate_of_birth() == null) map.put("date_of_birth", "Enter correct DOB.");
        if (user.getPassword() == null || user.getPassword().length()==0) map.put("password", "Inappropriate password.");
        if (user.getFname() == null || user.getFname().length() ==0) map.put("fname", "First name can not be empty.");
        if (user.getLname() == null || user.getLname().length()==0) map.put("lname", "Last name can not be empty.");
        if(map.size()==0) {
        String sql = "insert into user(User_name, Fname, Lname, Email_id, Date_of_birth, password) values(\"" +
                user.getUser_name() + "\",\"" + user.getFname()+  "\",\"" + user.getLname() + "\",\"" +  user.getEmail_id() + "\",\"" + user.getDate_of_birth() +
                "\",\"" + user.getPassword() + "\");";
        jdbcTemplate.execute(sql);}
        return map;
    }

    public String Get_Set_token(String user_name){
        Random random = new Random();

        String generatedString = random.ints(35, 91)
                .limit(8)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String sql = "update user set token = \"" + generatedString + "\" where User_name = \"" + user_name + "\";";
        jdbcTemplate.execute(sql);
        return generatedString;
    }

    public HashMap Login(user user){
        HashMap<String, String> map = new HashMap<>();
        if(user.getUser_name() == null || user.getUser_name().length()==0) map.put("user_name", "Username can not be empty.");
        if (user.getPassword() == null || user.getPassword().length()==0) map.put("password", "Inappropriate password.");
        user q = fetchuser(user.getUser_name());
        if(q == null) map.put("username", "Username incorrect.");
        else if(!Objects.equals(q.getPassword(), user.getPassword())) map.put("password", "Incorrect password.");
        if(map.size()==0) {
            map.put("token", Get_Set_token(user.getUser_name()));
            }
        return map;
    }

    public HashMap profileUpdate(user sender, user user){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(user == null) map.put("user", "insufficient data");
        if(map.size()>0) return map;
        String sql = "update user set Fname = \"" + user.getFname() + "\", Lname = \"" + user.getLname() + "\", Description = \"" + user.getDescription() + "\", Date_of_birth = \""+
        sender.getDate_of_birth() + "\" where User_name = \""
        + sender.getUser_name() + "\"";
        jdbcTemplate.execute(sql);
        return map;
    }

    public HashMap profileImageUpdate(user sender, profile_image_update_form profile_image_update){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(profile_image_update.img_url == null) map.put("img_url", "insufficient data");
        if(map.size()>0) return map;

        String sql = "update user set Profile_pic = \"" + profile_image_update.img_url + "\" where User_name = \"" + sender.getUser_name() + "\"";
        jdbcTemplate.execute(sql);
        return map;
    }

    public List<HashMap<String, String>> get_requests(user sender){
        List<HashMap<String, String>> list_map = new ArrayList<>();
        String sql = "select u.User_name, u.Fname, u.Lname from user as u , friend_requests as f where f.receiver = \"" + sender.getUser_name() + "\" and f.sender = u.User_name";
        List<user> a = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(user.class));

        for(int i = 0;i<a.size();i++){
            HashMap<String, String> map = new HashMap<>();
            map.put("username",a.get(i).getUser_name());
            map.put("first_name",a.get(i).getFname());
            map.put("last_name",a.get(i).getLname());
            map.put("type","0");
            list_map.add(map);
        }

        sql = "select g.Group_id, g.name, g.description from group_data as g, group_invites as gi where gi.User_name = \"" + sender.getUser_name() + "\" and g.Group_id = gi.Group_id";
        List<group_data> b = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(group_data.class));

        for(int i = 0;i<b.size();i++){
            HashMap<String, String> map = new HashMap<>();
            map.put("id", Integer.toString(b.get(i).getGroup_id()));
            map.put("name",b.get(i).getName());
            map.put("description",b.get(i).getDescription());
            map.put("type","1");
            list_map.add(map);
        }
        return list_map;
    }
}
