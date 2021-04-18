package com.gummybearr.jai.service;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.crawl.Crawl;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.recruitment.Recruitments;
import com.gummybearr.jai.domain.telegramBot.AdminBot;
import com.gummybearr.jai.domain.telegramBot.MessageBot;
import com.gummybearr.jai.domain.user.User;
import com.gummybearr.jai.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentService {


    private final CrawlService crawlService;

    private final UserRepository userRepository;

    @Scheduled(cron = Crawl.CRON_CRAWL_TERM)
    public void sendRecruitments() throws IOException {
        Recruitments recruitments = crawlService.recruitments();
        List<Message> messages = recruitments.messages();
        List<User> users = userRepository.findAll();
        
        users.parallelStream()
                .map(User::chatId)
                .map(chatId -> {
                    MessageBot messageBot = new MessageBot(Auth.Telegram.TOKEN);
                    messageBot.send(messages, Auth.Telegram.ADMIN_ID);
//                    messageBot.send(messages, chatId);
                    return null;
                })
                .collect(Collectors.toList());
        reportResult();
    }

    private void reportResult() {
        MessageBot messageBot = new MessageBot(Auth.Telegram.TOKEN);
//        messageBot.send(new Message("소영아 허리 피구 좋은 하루 보내♡"), Auth.Telegram.SOYOUNG_ID);
        AdminBot.send(new Message(new StringBuilder().append("프로그램이 성공적으로 실행을 마쳤습니다")));
    }

}
