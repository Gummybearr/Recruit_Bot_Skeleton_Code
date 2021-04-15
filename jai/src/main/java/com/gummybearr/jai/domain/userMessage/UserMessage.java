package com.gummybearr.jai.domain.userMessage;

import com.pengrad.telegrambot.model.Update;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long chatId;
    private Long messageId;
    private String message;

    public UserMessage(Update update){
        this.chatId = (long)update.message().from().id();
        this.messageId = (long)update.message().messageId();
        this.message = update.message().text();
    }

    public static List<UserMessage> userMessages(List<Update> updates){
        return updates.parallelStream()
                .map(UserMessage::new)
                .collect(Collectors.toList());
    }

    public Long getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public Long getMessageId() {
        return messageId;
    }
}
