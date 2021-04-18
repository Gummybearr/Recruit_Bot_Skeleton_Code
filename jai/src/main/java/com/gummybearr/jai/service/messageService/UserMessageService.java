package com.gummybearr.jai.service.messageService;

import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.message.UserMessageType;
import com.gummybearr.jai.domain.recruitment.Recruitment;
import com.gummybearr.jai.domain.recruitment.RecruitmentRepository;
import com.gummybearr.jai.domain.recruitment.Recruitments;
import com.gummybearr.jai.domain.telegramBot.MessageBot;
import com.gummybearr.jai.domain.telegramBot.ResultBot;
import com.gummybearr.jai.domain.user.User;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import com.gummybearr.jai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMessageService {

    private final MessageBot messageBot;
    private final ResultBot resultBot;

    private final UserService userService;

    private final RecruitmentRepository recruitmentRepository;

    public void handleUserMessage(UserMessageType userMessageType, Update update) {
        UserMessage userMessage = UserMessage.detachHead(update);
        userService.fetchUser(userMessage);

        if (userMessageType.equals(UserMessageType.SEARCH)) {
            handleSearch(userMessage);
        }
        if (userMessageType.equals(UserMessageType.BLACK_LIST)) {
            handleBlackList(userMessage);
        }
        if (userMessageType.equals(UserMessageType.WHITE_LIST)) {
            handleWhiteList(userMessage);
        }
    }

    private void handleSearch(UserMessage userMessage) {
        User user = userService.fetchUser(userMessage);

        if (userMessage.getMessage().trim().isBlank() && user.getWhiteList().isBlank()
                && user.getBlackList().isBlank()) {
            messageBot.send(Message.ofBlank(), userMessage.getChatId());
            return;
        }

        List<Recruitment> recruitments = recruitmentRepository.findRecruitmentLike(userMessage.getMessage());
        recruitments = recruitments.parallelStream()
                .filter(user::filterBlackList)
                .filter(user::filterWhiteList)
                .collect(Collectors.toList());
        List<Message> messages = new Recruitments(recruitments).messages();

        if (messages.isEmpty()) {
            messages.add(Message.ofNull());
        }
        messageBot.send(messages, userMessage.getChatId());
    }

    private void handleBlackList(UserMessage userMessage) {
        User user = userService.fetchUser(userMessage);
        if (userMessage.startsWith(UserMessageType.ADD)) {
            userMessage = UserMessage.detachHead(userMessage);
            user = user.addBlackList(userMessage.getMessage());
        }
        if (userMessage.startsWith(UserMessageType.DEL)) {
            userMessage = UserMessage.detachHead(userMessage);
            user = user.delBlackList(userMessage.getMessage());
        }
        userService.putUser(user);
        resultBot.send(userNoticeMessage("블랙리스트", user.getBlackList()), user.chatId());
    }

    private void handleWhiteList(UserMessage userMessage) {
        User user = userService.fetchUser(userMessage);
        if (userMessage.startsWith(UserMessageType.ADD)) {
            userMessage = UserMessage.detachHead(userMessage);
            user = user.addWhiteList(userMessage.getMessage());
        }
        if (userMessage.startsWith(UserMessageType.DEL)) {
            userMessage = UserMessage.detachHead(userMessage);
            user = user.delWhiteList(userMessage.getMessage());
        }
        userService.putUser(user);
        resultBot.send(userNoticeMessage("화이트리스트", user.getWhiteList()), user.chatId());
    }

    private String userNoticeMessage(String string, String noticeContent) {
        return String.format("\uD83D\uDC40%s 현황%n - %s", string, noticeContent);
    }

}
