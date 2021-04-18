package com.gummybearr.jai.controller;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.service.messageService.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BotController extends TelegramLongPollingBot {

    private final MessageService messageService;

    @Override
    public String getBotUsername() {
        return "RecruitSKKU_bot";
    }

    @Override
    public String getBotToken() {
        return Auth.Telegram.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        messageService.handleMessage(update);
    }
}
