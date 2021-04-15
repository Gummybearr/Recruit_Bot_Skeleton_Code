package com.gummybearr.jai.domain.telegramBot;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminBot {
    private static final TelegramBot bot = new TelegramBot(Auth.Telegram.adminToken);

//    public void send(Message message) {
//        bot.execute(new SendMessage(chatId, message.toString()));
//    }

    public static void send(List<UserMessage> userMessages) {
        List<Message> messages = Message.messages(userMessages);
        Message message = Message.mergedMessage(messages);
        send(message);
    }

    public static void send(Message message) {
        bot.execute(new SendMessage(Auth.Telegram.admin, message.toString()));
    }
}
