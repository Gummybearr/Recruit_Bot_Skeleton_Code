package com.gummybearr.jai.domain.telegramBot;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.message.Message;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class AdminBot {
    private static final TelegramBot bot = new TelegramBot(Auth.Telegram.ADMIN_TOKEN);

    public static void send(Message message) {
        bot.execute(new SendMessage(Auth.Telegram.ADMIN_ID, message.toString()));
    }

    public static void send(Exception exception) {
        bot.execute(new SendMessage(Auth.Telegram.ADMIN_ID, exception.toString()));
    }
}
