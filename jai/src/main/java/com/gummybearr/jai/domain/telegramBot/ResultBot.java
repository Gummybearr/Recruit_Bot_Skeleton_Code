package com.gummybearr.jai.domain.telegramBot;

import com.gummybearr.jai.constants.Auth;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class ResultBot {

    public static final String CRON_UPDATE_TERM = "*/4 * * * * *";

    private static final TelegramBot bot = new TelegramBot(Auth.Telegram.TOKEN);

    public void send(String stringMessage, long chatId) {
        bot.execute(new SendMessage(chatId, stringMessage));
    }

}
