package com.mediabase.messanger.controllers;

import com.mediabase.messanger.forms.fr_response_form;
import com.mediabase.messanger.forms.grp_request_form;
import com.mediabase.messanger.forms.grp_response_form;
import com.mediabase.messanger.tables.group_data;
import com.mediabase.messanger.tables.user;
import com.mediabase.messanger.tables_dao.friendDAO;
import com.mediabase.messanger.tables_dao.group_dataDAO;
import com.mediabase.messanger.tables_dao.userDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;



@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    userDAO userDAO;

    @Autowired
    friendDAO friendDAO;

    @Autowired
    group_dataDAO group_dataDAO;

    @PostMapping("/fr_request")
    ResponseEntity<HashMap> fr_request(@RequestHeader("token") String token, @RequestBody user user){
        user sender = userDAO.fetchuser_token(token);
        user receiver = userDAO.fetchuser(user.getUser_name());
        HashMap map = friendDAO.fr_request(sender, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/fr_response")
    ResponseEntity<HashMap> fr_response(@RequestHeader("token") String token, @RequestBody fr_response_form fr_form){
        user receiver = userDAO.fetchuser_token(token);
        user sender = userDAO.fetchuser(fr_form.user_name);
        System.out.println(fr_form.user_name);
        System.out.println(fr_form.bool);
        HashMap map = friendDAO.fr_response(sender, receiver, fr_form.bool);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/fr_remove")
    ResponseEntity<HashMap> fr_remove(@RequestHeader("token") String token, @RequestBody user user){
        user sender = userDAO.fetchuser_token(token);
        user receiver = userDAO.fetchuser(user.getUser_name());
        HashMap map = friendDAO.fr_remove(sender, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/grp_create")
    ResponseEntity<HashMap> grp_create(@RequestHeader("token") String token, @RequestBody group_data group){
        user sender = userDAO.fetchuser_token(token);
        HashMap map = group_dataDAO.grp_create(sender, group);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/grp_request")
    ResponseEntity<HashMap> grp_request(@RequestHeader("token") String token, @RequestBody grp_request_form grp_request){
        user sender = userDAO.fetchuser_token(token);
        group_data grp = group_dataDAO.fetchgroup(grp_request.grp_id);
        user receiver = userDAO.fetchuser(grp_request.user_name);
        HashMap map = group_dataDAO.grp_request(sender, grp, receiver);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/grp_response")
    ResponseEntity<HashMap> grp_response(@RequestHeader("token") String token, @RequestBody grp_response_form group){
        user sender = userDAO.fetchuser_token(token);
        group_data grp = group_dataDAO.fetchgroup(group.group_id);
        HashMap map = group_dataDAO.grp_response(sender, grp, group.bool);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/grp_exit")
    ResponseEntity<HashMap> grp_exit(@RequestHeader("token") String token, @RequestBody group_data group){
        user sender = userDAO.fetchuser_token(token);
        HashMap map = group_dataDAO.grp_exit(sender, group);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }
}
