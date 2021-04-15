package com.gummybearr.jai.domain.telegramBot;

import com.gummybearr.jai.constants.Auth;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateBot {

    public static final String CRON_UPDATE_TERM = "*/6 * * * * *";

    private static final TelegramBot bot = new TelegramBot(Auth.Telegram.token);
    private static GetUpdates getUpdates = new GetUpdates().limit(50).offset(0).timeout(3);

    public List<Update> updates() {
        bot.setUpdatesListener(updates -> UpdatesListener.CONFIRMED_UPDATES_ALL);
        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        bot.removeGetUpdatesListener();

        if (updatesResponse.updates() == null) {
            return new ArrayList<>();
        }
        return updatesResponse.updates();
    }

//    public List<Update> commands(List<Update> updates){
//        return updates.parallelStream()
//                .filter(update -> update.message())
//    }

}
