package com.gummybearr.jai.service.messageService;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.message.AdminMessageType;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.telegramBot.AdminBot;
import com.gummybearr.jai.domain.telegramBot.ResultBot;
import com.gummybearr.jai.domain.user.UserRepository;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMessageService {

    private final ResultBot resultBot;

    private final UserRepository userRepository;

    public void handleAdminMessage(AdminMessageType adminMessageType, Update update) {
        UserMessage userMessage = new UserMessage(update);
        if (!userMessage.isAdmin()) {
            return;
        }
        if (adminMessageType.equals(AdminMessageType.COUNT_USER)) {
            long count = userRepository.count();
            AdminBot.send(Message.ofCount(count));
        }
        if(adminMessageType.equals(AdminMessageType.BROADCAST)){
            userMessage = UserMessage.detachHead(userMessage);
            UserMessage finalUserMessage = userMessage;
            userRepository.findAll()
                    .parallelStream()
                    .map(user -> {
//                        resultBot.send(finalUserMessage.getMessage(), user.getChatId());
                        resultBot.send(finalUserMessage.getMessage(), Auth.Telegram.ADMIN_ID);
                        System.out.println(finalUserMessage.getMessage());
                        return null;
                    })
                    .collect(Collectors.toList());
        }
    }
}
