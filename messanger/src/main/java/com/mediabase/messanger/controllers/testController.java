package com.mediabase.messanger.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediabase.messanger.tables.user;
import com.mediabase.messanger.tables_dao.userDAO;

@RestController
public class testController {
    @Autowired
    userDAO userDAO;
    @GetMapping("/")
    public List<user> getAllList(){
        return userDAO.fetchall();
    }
}
