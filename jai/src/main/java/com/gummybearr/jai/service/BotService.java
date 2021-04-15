package com.gummybearr.jai.service;

import com.gummybearr.jai.domain.telegramBot.AdminBot;
import com.gummybearr.jai.domain.telegramBot.UpdateBot;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import com.gummybearr.jai.domain.userMessage.UserMessageRepository;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BotService {

    private final UpdateBot updateBot;

    private final UserMessageRepository userMessageRepository;

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

    @Scheduled(cron = UpdateBot.CRON_UPDATE_TERM)
    @Async("threadPoolTaskExecutor")
    public void newMessages() {
        List<Update> updates = updateBot.updates();
        List<UserMessage> userMessages = UserMessage.userMessages(updates);
        List<UserMessage> uniqueMessages = uniqueMessages(userMessages);
        userMessageRepository.saveAll(uniqueMessages);
        AdminBot.send(uniqueMessages);
    }

    private List<UserMessage> uniqueMessages(List<UserMessage> userMessages) {
        List<Long> messageIds = userMessages.stream()
                .map(UserMessage::getMessageId)
                .collect(Collectors.toList());

        List<Long> dupMessages = userMessageRepository.findByMessageIdIn(messageIds)
                .parallelStream()
                .map(UserMessage::getMessageId)
                .collect(Collectors.toList());

        return userMessages.parallelStream()
                .filter(userMessage -> !dupMessages.contains(userMessage.getMessageId()))
                .collect(Collectors.toList());
    }

}
