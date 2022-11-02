package com.mediabase.messanger.controllers;


import com.mediabase.messanger.tables.user;
import com.mediabase.messanger.tables_dao.userDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    userDAO userDAO;

    @PostMapping("/register")
    ResponseEntity<HashMap> register(@RequestBody user user){
        HashMap map = userDAO.Register(user);
        if(map.size() == 0) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    ResponseEntity<HashMap> login(@RequestBody user user){
        HashMap map = userDAO.Login(user);
        if(map.containsKey("token")) return new ResponseEntity<>(map, HttpStatus.OK);
        return new ResponseEntity<>( map, HttpStatus.BAD_REQUEST);
    }

}
