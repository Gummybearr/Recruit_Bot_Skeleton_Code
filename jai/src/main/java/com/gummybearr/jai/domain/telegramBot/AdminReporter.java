package com.gummybearr.jai.domain.telegramBot;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.message.Message;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;

public class AdminReporter {
    private static final TelegramBot bot = new TelegramBot(Auth.Telegram.adminToken);
    private final long chatId;

    public AdminReporter(long chatId) {
        this.chatId = chatId;
    }

    public void send(Message message) {
        bot.execute(new SendMessage(chatId, message.toString()));
    }
}
