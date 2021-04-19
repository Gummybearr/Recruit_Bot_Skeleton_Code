package com.gummybearr.jai.domain.message;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public enum AdminMessageType {
    COUNT_USER("#count user"),
    VIEW_MEMORY("#view memory"),
    BROADCAST("#broadcast"),

    OTHERS("#others");

    private String type;

    AdminMessageType(String type) {
        this.type = type;
    }

    public static AdminMessageType of(Update update) {
        Message message = new Message(update);

        return Arrays.stream(values())
                .filter(v -> message.startsWith(v.type))
                .findFirst()
                .orElse(OTHERS);
    }
}
