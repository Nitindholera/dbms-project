package com.mediabase.messanger.controllers;

import com.mediabase.messanger.forms.*;
import com.mediabase.messanger.tables.*;
import com.mediabase.messanger.tables_dao.chatDAO;
import com.mediabase.messanger.tables_dao.friendDAO;
import com.mediabase.messanger.tables_dao.group_dataDAO;
import com.mediabase.messanger.tables_dao.userDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    userDAO userDAO;

    @Autowired
    friendDAO friendDAO;

    @Autowired
    chatDAO chatDAO;

    @Autowired
    group_dataDAO group_dataDAO;

    @CrossOrigin
    @PostMapping("/fr_request")
    ResponseEntity<HashMap> fr_request(@RequestHeader("token") String token, @RequestBody user user){
        user sender = userDAO.fetchuser_token(token);
        user receiver = userDAO.fetchuser(user.getUser_name());
        HashMap map = friendDAO.fr_request(sender, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/fr_response")
    ResponseEntity<HashMap> fr_response(@RequestHeader("token") String token, @RequestBody fr_response_form fr_form){
        user receiver = userDAO.fetchuser_token(token);
        user sender = userDAO.fetchuser(fr_form.user_name);
        HashMap map = friendDAO.fr_response(sender, receiver, fr_form.bool);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/fr_remove")
    ResponseEntity<HashMap> fr_remove(@RequestHeader("token") String token, @RequestBody user user){
        user sender = userDAO.fetchuser_token(token);
        user receiver = userDAO.fetchuser(user.getUser_name());
        HashMap map = friendDAO.fr_remove(sender, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/grp_create")
    ResponseEntity<HashMap> grp_create(@RequestHeader("token") String token, @RequestBody group_data group){
        user sender = userDAO.fetchuser_token(token);
        HashMap map = group_dataDAO.grp_create(sender, group);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/grp_request")
    ResponseEntity<HashMap> grp_request(@RequestHeader("token") String token, @RequestBody grp_request_form grp_request){
        user sender = userDAO.fetchuser_token(token);
        group_data grp = group_dataDAO.fetchgroup(grp_request.grp_id);
        user receiver = userDAO.fetchuser(grp_request.user_name);
        HashMap map = group_dataDAO.grp_request(sender, grp, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/grp_response")
    ResponseEntity<HashMap> grp_response(@RequestHeader("token") String token, @RequestBody grp_response_form group){
        user sender = userDAO.fetchuser_token(token);
        group_data grp = group_dataDAO.fetchgroup(group.group_id);
        HashMap map = group_dataDAO.grp_response(sender, grp, group.bool);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/grp_exit")
    ResponseEntity<HashMap> grp_exit(@RequestHeader("token") String token, @RequestBody group_data group){
        user sender = userDAO.fetchuser_token(token);
        HashMap map = group_dataDAO.grp_exit(sender, group);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/grp_member_remove")
    ResponseEntity<HashMap> grp_member_remove(@RequestHeader("token") String token, @RequestBody grp_request_form group){
        user sender = userDAO.fetchuser_token(token);
        group_data grp = group_dataDAO.fetchgroup(group.grp_id);
        user receiver = userDAO.fetchuser(group.user_name);
        HashMap map = group_dataDAO.group_member_remove(sender, grp, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }
    
    @CrossOrigin
    @PostMapping("/grp_admin_create")
    ResponseEntity<HashMap> grp_admin_create(@RequestHeader("token") String token, @RequestBody grp_request_form grp_request){
        user sender = userDAO.fetchuser_token(token);
        group_data grp = group_dataDAO.fetchgroup(grp_request.grp_id);
        user receiver = userDAO.fetchuser(grp_request.user_name);
        HashMap map = group_dataDAO.grp_admin_create(sender, grp, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }
    
    @CrossOrigin
    @PostMapping("/grp_admin_remove")
    ResponseEntity<HashMap> grp_admin_remove(@RequestHeader("token") String token, @RequestBody grp_request_form grp_request){
        user sender = userDAO.fetchuser_token(token);
        group_data grp = group_dataDAO.fetchgroup(grp_request.grp_id);
        user receiver = userDAO.fetchuser(grp_request.user_name);
        HashMap map = group_dataDAO.grp_admin_remove(sender, grp, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }
    
    @CrossOrigin
    @PostMapping("/profileUpdate")
    ResponseEntity<HashMap> profileUpdate(@RequestHeader("token") String token, @RequestBody user user){
        user sender = userDAO.fetchuser_token(token);
        HashMap map = userDAO.profileUpdate(sender, user);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }
    
    @CrossOrigin
    @PostMapping("/groupUpdate")
    ResponseEntity<HashMap> groupUpdate(@RequestHeader("token") String token, @RequestBody group_data group){
        user sender = userDAO.fetchuser_token(token);

        HashMap map = group_dataDAO.groupUpdate(sender, group);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/groupImageUpdate")
    ResponseEntity<HashMap>groupImageUpdate(@RequestHeader("token") String token, @RequestBody grp_image_update_form grp_image_update){
        user sender = userDAO.fetchuser_token(token);
        HashMap map = group_dataDAO.groupImageUpdate(sender, grp_image_update);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/profileImageUpdate")
    ResponseEntity<HashMap>profileImmageUpdate(@RequestHeader("token") String token, @RequestBody profile_image_update_form profile_image_update){
        user sender = userDAO.fetchuser_token(token);

        HashMap map = userDAO.profileImageUpdate(sender, profile_image_update);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PostMapping("/retrieve_message")
    ResponseEntity<List<message>> retrieve_message(@RequestHeader("token") String token, @RequestBody retrieve_msg_form r_form){
        user sender = userDAO.fetchuser_token(token);
        Integer ch_id = null;
        String[] st = r_form.title.split("-");
        if(Objects.equals(st[0], "fr")){
            friend f = friendDAO.fetchFriend(st[1], st[2]);
            ch_id = f.getChat_id();
        }
        else {
            group_data g = group_dataDAO.fetchgroup(Integer.valueOf(st[1]));
            ch_id = g.getChat_id();
        }
        if(sender == null || ch_id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        chat ch = chatDAO.fetchChat(ch_id);
        return new ResponseEntity<>(chatDAO.retrieve_message(ch, r_form.index), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/friends")
    ResponseEntity<List<get_friend_form>> GetFriends(@RequestHeader("token") String token){
        user sender = userDAO.fetchuser_token(token);
        if(sender == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(friendDAO.get_friends(sender), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/groups")
    ResponseEntity<List<HashMap<String, String>>> GetGroups(@RequestHeader("token") String token){
        user sender = userDAO.fetchuser_token(token);
        if(sender == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(group_dataDAO.getGroups(sender), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/profile/{user_name}")
    ResponseEntity<HashMap> Profile(@PathVariable(value = "user_name") String username){
        user u = userDAO.fetchuser(username);
        if(u == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        HashMap<String, String> map = new HashMap<>();
        map.put("username", u.getUser_name());
        map.put("status", u.getDescription());
        map.put("frcount", "0");
        map.put("fname", u.getFname());
        map.put("lname", u.getLname());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/group/{id}")
    ResponseEntity<HashMap<String, String>> Group(@PathVariable(value = "id") String id){
        group_data g = group_dataDAO.fetchgroup(Integer.valueOf(id));
        if(g == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", g.getName());
        map.put("description", g.getDescription());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/requests")
    ResponseEntity<List<HashMap<String, String>>> requests(@RequestHeader("token") String token){
        user sender = userDAO.fetchuser_token(token);
        if(sender == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userDAO.get_requests(sender), HttpStatus.OK); 
    }

    @CrossOrigin
    @GetMapping("/profileImage/{type}/{title}")
    ResponseEntity<HashMap<String, String>> GetImage(@PathVariable(value = "type") Integer type,@PathVariable(value = "title") String title){
        HashMap<String, String> map = new HashMap<>();
        if(type==0){
            user u = userDAO.fetchuser(title);
            if(u.getProfile_pic() != null) map.put("img_url", u.getProfile_pic());
            else map.put("img_url", "https://eitrawmaterials.eu/wp-content/uploads/2016/09/person-icon.png");
        }
        else if (type == 1) {
            group_data g = group_dataDAO.fetchgroup(Integer.valueOf(title));
            if(g.getPicture() != null) map.put("img_url", g.getPicture());
            else map.put("img_url", "https://www.iconpacks.net/icons/1/free-user-group-icon-296-thumb.png");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/groupMemberList")
    ResponseEntity<HashMap<String, List<HashMap<String, String>>>>groupMemberList(@RequestHeader("token") String token, @RequestBody group_data group){
        user sender = userDAO.fetchuser_token(token);
        HashMap<String, List<HashMap<String, String>>> map = group_dataDAO.get_group_members(sender, group);
        if(sender == null || map == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
