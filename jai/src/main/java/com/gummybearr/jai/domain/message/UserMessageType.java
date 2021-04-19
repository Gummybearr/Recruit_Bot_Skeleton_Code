package com.gummybearr.jai.domain.message;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

public enum UserMessageType {
    WHITE_LIST("/whitelist"),
    BLACK_LIST("/blacklist"),
    SEARCH("/search"),
    ALARM("/alarm"),
    HELP("/help"),

    OTHERS("/others");

    public static final String ADD = "add";
    public static final String DEL = "del";
    private String type;

    UserMessageType(String type) {
        this.type = type;
    }

    public static UserMessageType of(Update update) {
        Message message = new Message(update);

        return Arrays.stream(values())
                .filter(v -> message.startsWith(v.type))
                .findFirst()
                .orElse(OTHERS);
    }
}
