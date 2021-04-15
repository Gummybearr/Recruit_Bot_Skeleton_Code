package com.gummybearr.jai.domain.message;

import com.gummybearr.jai.domain.recruitment.Recruitment;
import com.gummybearr.jai.domain.userMessage.UserMessage;

import java.util.List;
import java.util.stream.Collectors;

public class Message {

    private String message;

    public Message(UserMessage userMessage){
        this(userMessage.getMessage());
    }

    public Message(String message) {
        this.message = message;
    }

    public static Message mergedMessage(List<Recruitment> recruitments, int startIdx, int maxIdx) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int idx = startIdx; idx < maxIdx; idx++) {
            stringBuilder.append(recruitments.get(idx));
        }
        return new Message(String.valueOf(stringBuilder));
    }

    public static Message mergedMessage(List<Message> messages){
        StringBuilder stringBuilder = new StringBuilder();
        for (Message message:messages){
            stringBuilder.append(message.message);
            stringBuilder.append("\n");
        }
        return new Message(String.valueOf(stringBuilder));
    }

    public static List<Message> messages(List<UserMessage> userMessages) {
        return userMessages.parallelStream()
                .map(Message::new)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return message;
    }
}
