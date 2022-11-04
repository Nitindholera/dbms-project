package com.mediabase.messanger.tables_dao;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mediabase.messanger.forms.grp_image_update_form;
import com.mediabase.messanger.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class group_dataDAO {
    @Autowired
    chatDAO chatDAO;

    @Autowired
    userDAO userDAO;
    
    @Autowired
    messageDAO messageDAO;


    private JdbcTemplate jdbcTemplate;

    public group_dataDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<group_data> fetchall(){
        String sql = "SELECT * FROM group_data";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(group_data.class));
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
        String sql = "select max(Group_id) as Group_id from group_data";
        List<group_data> a = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(group_data.class));
        int g_id = 0;
        if(a.get(0).getGroup_id()!=null)  g_id = a.get(0).getGroup_id() + 1;
        sql = "insert into group_data(Group_id, name, chat_id, description) values("+ g_id + ", \"" + group.getName() + "\","+  Chat_id + ", \"" + group.getDescription() +"\"); ";
        jdbcTemplate.execute(sql);
        sql = "insert into is_member_group(Group_id, User_name, is_admin) values(" + g_id + ", \"" + sender.getUser_name() + "\", 1);";
        jdbcTemplate.execute(sql);
        return map;
    }

    public HashMap grp_request(user sender, group_data group, user receiver){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(group == null) map.put("group", "Group not found.");
        if(receiver == null) map.put("receiver", "User not found");
        if(map.size()>0) return map;
        String sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + " \" and is_admin = 1";

        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(is_member_group.class));
        if(x.size() != 0) map.put("sender", "Sender not admin");
        if(map.size()>0) return map;

        sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + receiver.getUser_name() + "\"";
        x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(is_member_group.class));
        if(x.size() != 0) map.put("receiver", "Already a group member");
        if(map.size()==0){
            sql = "select * from group_invites where Group_id = " + group.getGroup_id() + " and User_name = \"" + receiver.getUser_name() + "\"";
            List<group_invites> y = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(group_invites.class));
            if (y.size()==0) {
                sql = "insert into group_invites(User_name, Group_id) values(\"" + receiver.getUser_name() + "\"," + group.getGroup_id() + ")";
                jdbcTemplate.execute(sql);
            }
        }
        return map;
    }

    public HashMap grp_response(user sender, group_data group, Boolean bool){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(group == null) map.put("group", "Group not found.");
        if(map.size()>0) return map;
        String sql = "select * from group_invites where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\"";

        List<group_invites> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(group_invites.class));
        if(x.size() == 0) map.put("sender", "Request not found.");
        if(map.size()==0){
            sql = "DELETE FROM group_invites where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\"";
            jdbcTemplate.execute(sql);
            if(bool) {
                sql = "insert into is_member_group(Group_id, User_name, is_admin) values(" + group.getGroup_id() + ", \"" + sender.getUser_name() + "\", 0);";
                jdbcTemplate.execute(sql);
            }
        }
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

    public HashMap group_member_remove(user sender, group_data grp, user receiver){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(grp == null) map.put("group", "Group not found.");
        if(receiver == null) map.put("receiver", "User not found");
        if(map.size()>0) return map;

        String sql = "select * from is_member_group where Group_id = " + grp.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\" and is_admin = 1";        
        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        if(x.size() == 0) map.put("group", "sender is not an admin or sender is not in that grp");
        if(map.size()>0) return map;
        
        sql = "select * from is_member_group where Group_id = " + grp.getGroup_id() + " and User_name = \"" + receiver.getUser_name() + "\"";        
        x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(is_member_group.class));
        
        if(x.size() == 0) map.put("group", "receiver is not a member of this group");
        if(map.size()>0) return map;

        sql = "delete from is_member_group where Group_id = " + grp.getGroup_id() + " and User_name = \"" + receiver.getUser_name() + "\"";
            jdbcTemplate.execute(sql);
        return map;
    }

    public HashMap grp_admin_create(user sender, group_data grp, user receiver){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(grp == null) map.put("group", "Group not found.");
        if(receiver == null) map.put("receiver", "User not found");
        if(map.size()>0) return map;

        String sql = "select * from is_member_group where Group_id = " + grp.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\" and is_admin = 1";        
        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        if(x.size() == 0) map.put("group", "sender is not an admin or sender is not in that grp");
        if(map.size()>0) return map;
        
        sql = "select * from is_member_group where Group_id = " + grp.getGroup_id() + " and User_name = \"" + receiver.getUser_name() + "\" and is_admin = 0";        
        x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        
        if(x.size() == 0) map.put("group", "receiver is not a member of this group or receiver is already admin");
        if(map.size()>0) return map;

        sql = "update is_member_group set is_admin = 1 where User_name = \"" + receiver.getUser_name() + "\" and Group_id = " + grp.getGroup_id();
        jdbcTemplate.execute(sql);
        return map; 
    }

    public HashMap grp_admin_remove(user sender, group_data grp, user receiver){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(grp == null) map.put("group", "Group not found.");
        if(receiver == null) map.put("receiver", "User not found");
        if(map.size()>0) return map;

        String sql = "select * from is_member_group where Group_id = " + grp.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\" and is_admin = 1";        
        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        if(x.size() == 0) map.put("group", "sender is not an admin or sender is not in that grp");
        if(map.size()>0) return map;
        
        sql = "select * from is_member_group where Group_id = " + grp.getGroup_id() + " and User_name = \"" + receiver.getUser_name() + "\" and is_admin = 1";        
        x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        
        if(x.size() == 0) map.put("group", "receiver is not admin or not in group");
        if(map.size()>0) return map;

        sql = "update is_member_group set is_admin = 0 where User_name = \"" + receiver.getUser_name() + "\" and Group_id = " + grp.getGroup_id();
        jdbcTemplate.execute(sql);
        return map; 
    }

    public HashMap groupUpdate(user sender, group_data group){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(group.getName() == null || group.getDescription() == null || group.getGroup_id()==null) map.put("group", "insufficient data");
        if(map.size()>0) return map;
        
        String sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\" and is_admin = 1";        
        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        if(x.size() == 0) map.put("group", "sender is not an admin or sender is not in that grp");
        if(map.size()>0) return map;

        sql = "update group_data set name = \"" + group.getName() + "\", description = \"" + group.getDescription() + "\" where Group_id = " + group.getGroup_id();
        jdbcTemplate.execute(sql);
        return map;
    }

    public List<HashMap<String, String>> getGroups(user sender){
        List<HashMap<String, String>> map = new ArrayList<>();
        String sql = "select gd.Group_id, gd.name, gd.description, gd.picture, gd.chat_id\n" +
                "from group_data as gd, is_member_group as img\n" +
                "where gd.Group_id = img.Group_id and img.User_name = \"" + sender.getUser_name() + "\";";
        List<group_data> r = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(group_data.class));
        for(group_data gd: r){
            HashMap<String, String> p = new HashMap<>();
            p.put("id", gd.getGroup_id().toString());
            p.put("name", gd.getName());
            p.put("description", gd.getDescription());
            message m = messageDAO.last_message(gd.getChat_id());
            LocalDateTime d = LocalDateTime.parse("2007-12-03T10:15:30");
            if (m == null) p.put("last_act", d.toString());
            else p.put("last_act", m.getTime().toString());
            p.put("room", "grp_" + gd.getChat_id().toString());
            p.put("unseen", "0");
            if(gd.getPicture()==null) p.put("img_url", "https://www.iconpacks.net/icons/1/free-user-group-icon-296-thumb.png");
            else p.put("img_url", gd.getPicture());
            map.add(p);
        }
        return map;
    }
    public HashMap groupImageUpdate(user sender, grp_image_update_form grp_image_update){
        HashMap<String, String> map = new HashMap<>();
        if(sender == null) map.put("sender", "Unauthorized access.");
        if(grp_image_update.grp_id == null || grp_image_update.img_url == null) map.put("group", "insufficient data");
        if(map.size()>0) return map;

        String sql = "select * from is_member_group where Group_id = " + grp_image_update.grp_id + " and User_name = \"" + sender.getUser_name() + "\" and is_admin = 1";        
        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        if(x.size() == 0) map.put("group", "sender is not an admin or sender is not in that grp");
        if(map.size()>0) return map;

        sql = "update group_data set picture = \"" + grp_image_update.img_url + "\" where Group_id = " + grp_image_update.grp_id;
        jdbcTemplate.execute(sql);
        return map;
    }

    public HashMap<String, List<HashMap<String, String>>> get_group_members(user sender, group_data group){
        HashMap<String, List<HashMap<String, String>>> map = new HashMap<>();
        
        String sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\"";        
        List<is_member_group> x = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        if(x.size()==0) return null;

        sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and is_admin = 0 and User_name != \"" + sender.getUser_name() + "\"";
        List<is_member_group> y = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        
        List<HashMap<String, String>> arr1 = new ArrayList<>();
        for(int i = 0;i<y.size(); i++){
            HashMap<String, String> mp = new HashMap<>();
            user u = userDAO.fetchuser(y.get(i).getUser_name());
            mp.put("username", u.getUser_name());
            mp.put("first_name", u.getFname());
            mp.put("last_name", u.getLname());
            mp.put("isAdmin", "0");
            arr1.add(mp);
        }
        map.put("members", arr1);

        sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and is_admin = 1 and User_name != \"" + sender.getUser_name() + "\"";
        List<is_member_group> z = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));

        List<HashMap<String, String>> arr2 = new ArrayList<>();
        for(int i = 0;i<z.size(); i++){
            HashMap<String, String> mp = new HashMap<>();
            user u = userDAO.fetchuser(z.get(i).getUser_name());
            mp.put("username", u.getUser_name());
            mp.put("first_name", u.getFname());
            mp.put("last_name", u.getLname());
            mp.put("isAdmin", "1");
            arr2.add(mp);
        }
        map.put("admins", arr2);

        List<HashMap<String, String>> arr3 = new ArrayList<>();

        sql = "select * from is_member_group where Group_id = " + group.getGroup_id() + " and User_name = \"" + sender.getUser_name() + "\"";
        List<is_member_group> z1 = jdbcTemplate.query(sql, new BeanPropertyRowMapper<is_member_group>(is_member_group.class));
        HashMap<String, String> mp = new HashMap<>();
        user u = userDAO.fetchuser(z1.get(0).getUser_name());
        mp.put("usernmae", u.getUser_name());
        mp.put("first_name", u.getFname());
        mp.put("last_name", u.getLname());
        if(z1.get(0).getIs_admin())
            mp.put("isAdmin","1");
        else
            mp.put("isAdmin","0");

        arr3.add(mp);
        map.put("user", arr3);
        return map;
    }
}
