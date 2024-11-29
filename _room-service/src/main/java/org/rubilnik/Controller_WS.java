package org.rubilnik;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class Controller_WS {
    @MessageMapping(value = "/join")
    @SendTo(value = "/topic/public")
    public void join(){
        
    }
}