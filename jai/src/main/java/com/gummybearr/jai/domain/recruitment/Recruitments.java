package com.gummybearr.jai.domain.recruitment;

import com.gummybearr.jai.domain.message.Message;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@ToString
public class Recruitments {

    private static final int MERGE_MESSAGE_SIZE = 50;

    private List<Recruitment> recruitments;

    public Recruitments(List<Recruitment> recruitments) {
        this.recruitments = recruitments;
    }

    public List<Message> messages() {
        List<Message> messages = new ArrayList<>();
        for (int idx = 0; idx < this.recruitments.size(); idx += MERGE_MESSAGE_SIZE) {
            messages.add(Message.mergedMessage(this.recruitments, idx, min(recruitments.size(), idx + 50)));
        }
        return messages;
    }

    public List<Recruitment> recruitments() {
        return this.recruitments;
    }
}
