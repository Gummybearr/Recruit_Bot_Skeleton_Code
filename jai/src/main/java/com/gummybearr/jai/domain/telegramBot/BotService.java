package com.gummybearr.jai.domain.telegramBot;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.message.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotService {

//    public void messages(List<Message> messages) {
//        messages.parallelStream()
//                .map(this::sendOrAlertAdmin)
//                .collect(Collectors.toList());
//    }

//    private boolean sendOrAlertAdmin(Message message) {
//        try {
//            new Bot(Auth.Telegram.admin).send(message);
//            return true;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

}
