package com.gummybearr.jai.domain.message;

import com.gummybearr.jai.domain.recruitment.Recruitment;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Message {

    private static final String NULL_STRING = "찾으시는 결과가 데이터베이스에 존재하지 않습니다😿";
    private static final String BLANK_STRING = "빈 문자열은 허용하지 않습니다😿";

    private final String message;

    public Message(UserMessage userMessage) {
        this.message = userMessage.getMessage();
    }

    public Message(String message) {
        this.message = URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    public Message(Update update) {
        this.message = update.getMessage().getText();
    }

    public Message(StringBuilder stringBuilder) {
        this.message = String.valueOf(stringBuilder);
    }

    public static Message mergedMessage(List<Recruitment> recruitments, int startIdx, int maxIdx) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int idx = startIdx; idx < maxIdx; idx++) {
            stringBuilder.append(recruitments.get(idx));
            stringBuilder.append("\n");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n"));
        return new Message(String.valueOf(stringBuilder));
    }

    public static Message mergedMessage(List<Message> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Message message : messages) {
            stringBuilder.append(message.message);
            stringBuilder.append("\n");
        }
        return new Message(stringBuilder);
    }

    public static List<Message> messages(List<UserMessage> userMessages) {
        return userMessages.parallelStream()
                .map(Message::new)
                .collect(Collectors.toList());
    }

    public static Message ofCount(long count) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("count: ");
        stringBuilder.append(count);

        return new Message(stringBuilder);
    }

    public static Message ofNull() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NULL_STRING);
        return new Message(stringBuilder);
    }

    public static Message ofBlank() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BLANK_STRING);
        return new Message(stringBuilder);
    }

    public boolean startsWith(String string) {
        return this.message.startsWith(string);
    }

    @Override
    public String toString() {
        return message;
    }
}
