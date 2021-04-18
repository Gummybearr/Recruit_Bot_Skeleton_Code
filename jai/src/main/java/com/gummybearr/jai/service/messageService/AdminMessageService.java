package com.gummybearr.jai.service.messageService;

import com.gummybearr.jai.domain.message.AdminMessageType;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.telegramBot.AdminBot;
import com.gummybearr.jai.domain.user.UserRepository;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class AdminMessageService {

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
    }
}
