package com.mediabase.messanger.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.mediabase.messanger.tables.user;
import com.mediabase.messanger.tables.user_phone_number;
// import com.mediabase.messanger.tables_dao.userDAO;
import com.mediabase.messanger.tables_dao.user_phone_numberDAO;

@RestController
public class testController {
    @Autowired
    // userDAO userDAO;
    user_phone_numberDAO user_phone_numberDAO;
    @GetMapping("/")
    public List<user_phone_number> getAllList(){
        return user_phone_numberDAO.fetchall();
    }
}
