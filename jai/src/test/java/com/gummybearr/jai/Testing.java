package com.gummybearr.jai;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.telegramBot.Bot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Testing {
    @Test
    void send() {
        long start = System.currentTimeMillis();
        long chatId = Auth.Telegram.admin;
        List<Message> messages = new ArrayList<>();
        for (int count = 1; count <= 2000; count++) {
            messages.add(new Message(String.valueOf(count)));
        }
        Bot bot = new Bot(Auth.Telegram.token);
        bot.send(messages, chatId);
        long end = System.currentTimeMillis();
        System.out.println("it took " + (end-start) + " ms");
    }

//    @Test
//    void test() throws InterruptedException {
    //        long chatId = 808560264;
//        TelegramBot bot = new TelegramBot(Auth.Telegram.token);
//
//        bot.setUpdatesListener(updates -> UpdatesListener.CONFIRMED_UPDATES_ALL);
//
//        GetUpdates getUpdates = new GetUpdates().limit(10).offset(0).timeout(5);
//        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
//
//        if(updatesResponse.updates()==null){
//            System.out.println("null ok");
//        }
//        else{
//            //updatesResponse가 null이면 nullpoint exception 뜸
//            for (Update update : updatesResponse.updates()) {
//                System.out.println(update.updateId());
//                System.out.println(update.message().text());
//            }
//        }
//
//    }
}
