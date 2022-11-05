package com.mediabase.messanger.WebSocket;


import com.mediabase.messanger.tables.friend;
import com.mediabase.messanger.tables.group_data;
import com.mediabase.messanger.tables.is_member_group;
import com.mediabase.messanger.tables_dao.friendDAO;
import com.mediabase.messanger.tables_dao.group_dataDAO;
import com.mediabase.messanger.tables_dao.messageDAO;
import com.mediabase.messanger.tables_dao.userDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Objects;


@Controller
public class MessageController {
    private final SimpMessagingTemplate template;

    @Autowired
    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    userDAO userDAO;

    @Autowired
    group_dataDAO group_dataDAO;

    @Autowired
    friendDAO friendDAO;

    @Autowired
    messageDAO messageDAO;

    @MessageMapping("/hello/{username}")
    public Message_class greeting(Message_class message, @DestinationVariable String username) throws Exception {
        Thread.sleep(100);
        String[] st = message.getRoom().split("-");
        if(Objects.equals(st[0], "fr")){
            String t1 = userDAO.fetchuser(st[1]).getToken();
            String t2 = userDAO.fetchuser(st[2]).getToken();
            friend f = friendDAO.fetchFriend(st[1], st[2]);
            messageDAO.new_message(f.getChat_id(), message);
            this.template.convertAndSend("/topic/greetings/" + t1, message);
            this.template.convertAndSend("/topic/greetings/" + t2, message);
        }
        else {
            group_data g = group_dataDAO.fetchgroup(Integer.valueOf(st[1]));
            messageDAO.new_message(g.getChat_id(), message);
            for(is_member_group im: group_dataDAO.getMembers(Integer.valueOf(st[1]))){
                String t = userDAO.fetchuser(im.getUser_name()).getToken();
                this.template.convertAndSend("/topic/greetings/" + t, message);
            }
        }
        return message;
    }
}
