package com.gummybearr.jai.service.messageService;

import com.gummybearr.jai.domain.message.AdminMessageType;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.message.UserMessageType;
import com.gummybearr.jai.domain.telegramBot.AdminBot;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import com.gummybearr.jai.domain.userMessage.UserMessageRepository;
import com.gummybearr.jai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserMessageRepository userMessageRepository;

    private final UserService userService;
    private final AdminMessageService adminMessageService;
    private final UserMessageService userMessageService;

    public void handleMessage(Update update) {
        AdminBot.send(new Message(update));

        UserMessage userMessage = new UserMessage(update);
        userService.fetchUser(userMessage);

        AdminMessageType adminMessageType = AdminMessageType.of(update);
        if (adminMessageType != AdminMessageType.OTHERS) {
            adminMessageService.handleAdminMessage(adminMessageType, update);
        }
        UserMessageType userMessageType = UserMessageType.of(update);
        if (userMessageType != UserMessageType.OTHERS) {
            userMessageService.handleUserMessage(userMessageType, update);
        }
        userMessageRepository.save(userMessage);
    }

}
