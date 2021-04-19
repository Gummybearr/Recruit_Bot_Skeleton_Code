package com.gummybearr.jai.domain.userMessage;

import com.gummybearr.jai.constants.Auth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long chatId;
    private Long messageId;
    private String message;

    public UserMessage(Update update) {
        this.chatId = update.getMessage().getFrom().getId();
        this.messageId = (long) update.getMessage().getMessageId();
        this.message = update.getMessage().getText().trim();
    }

    private UserMessage(long chatId, long messageId, String message) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.message = message.trim();
    }

    public static UserMessage detachHead(Update update) {
        long chatId = update.getMessage().getFrom().getId();
        long messageId = (long) update.getMessage().getMessageId();
        String message = detachHead(update.getMessage().getText());
        return new UserMessage(chatId, messageId, message);
    }

    public static UserMessage detachHead(UserMessage userMessage) {
        return new UserMessage(userMessage.chatId, userMessage.messageId,
                detachHead(userMessage.message));
    }

    private static String detachHead(String string) {
        List<String> splitString = Arrays.stream(string.split(" "))
                .collect(Collectors.toList());
        return String.join(" ", splitString.subList(1, splitString.size())).trim();
    }

    public boolean isAdmin() {
        return this.chatId.equals(Auth.Telegram.ADMIN_ID);
    }

    public boolean startsWith(String string) {
        return message.trim()
                .startsWith(string);
    }
}
