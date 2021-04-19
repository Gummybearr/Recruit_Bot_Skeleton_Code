package com.gummybearr.jai.service;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.constants.SoyoungPool;
import com.gummybearr.jai.domain.crawl.Crawl;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.recruitment.Recruitment;
import com.gummybearr.jai.domain.recruitment.Recruitments;
import com.gummybearr.jai.domain.telegramBot.AdminBot;
import com.gummybearr.jai.domain.telegramBot.MessageBot;
import com.gummybearr.jai.domain.telegramBot.ResultBot;
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

    private final ResultBot resultBot;
    private final CrawlService crawlService;
    private final UserRepository userRepository;

    @Scheduled(cron = Crawl.CRON_CRAWL_TERM)
    public void sendRecruitments() throws IOException {
        Recruitments recruitments = crawlService.recruitments();
        List<User> users = userRepository.findAll();

        users.parallelStream()
                .map(user -> {
                    MessageBot messageBot = new MessageBot(Auth.Telegram.TOKEN);
                    List<Recruitment> filteredRecruitments = recruitments.recruitments()
                            .parallelStream()
                            .filter(user::filterBlackList)
                            .filter(user::filterWhiteList)
                            .collect(Collectors.toList());
                    List<Message> messages = new Recruitments(filteredRecruitments).messages();
                    messageBot.send(messages, user.getChatId());
                    return null;
                })
                .collect(Collectors.toList());
        reportResult();
    }

    private void reportResult() {
        resultBot.send(SoyoungPool.ofRandom(), Auth.Telegram.SOYOUNG_ID);
        AdminBot.send(new Message(new StringBuilder().append("프로그램이 성공적으로 실행을 마쳤습니다")));
    }

}
