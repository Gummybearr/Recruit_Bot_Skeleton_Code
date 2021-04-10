package com.gummybearr.jai.domain.message;

import com.gummybearr.jai.domain.recruitment.Recruitment;

import java.util.List;

public class Message {

    private String message;

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

    @Override
    public String toString() {
        return message;
    }
}
