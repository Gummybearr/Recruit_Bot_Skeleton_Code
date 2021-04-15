package com.gummybearr.jai.domain.admin;

import com.gummybearr.jai.constants.Auth;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.recruitment.Recruitment;
import com.gummybearr.jai.domain.recruitment.RecruitmentRepository;
import com.gummybearr.jai.domain.recruitment.Recruitments;
import com.gummybearr.jai.domain.telegramBot.MessageBot;
import com.gummybearr.jai.domain.user.UserRepository;
import com.gummybearr.jai.service.BotService;
import com.gummybearr.jai.service.CrawlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final CrawlService crawlService;
    private final BotService botService;

    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    private Recruitments recruitments;

    @GetMapping(path = "admin/load-data")
    public void loadData() throws IOException {
        Recruitments recruitments = crawlService.recruitments();
        this.recruitments = recruitments;
        List<Message> messages = recruitments.messages();
//        botService.messages(messages);
    }

    @GetMapping(path = "admin/query-data")
    public void queryData() {
        List<Long> hashValues = recruitments.recruitments()
                .stream()
                .map(Recruitment::hashedValue)
                .collect(Collectors.toList());
        recruitmentRepository.findRecruitmentsByHashedValueIn(hashValues);
    }

    @GetMapping(path = "admin/query-like")
    public void queryLike() {
        botService.newMessages();
    }

    @GetMapping(path = "admin/listenerTesting")
    public void queryListener() {
        System.out.println(recruitmentRepository.findRecruitmentLike("네이버"));
    }

    @GetMapping(path = "admin/test")
    public void send() {
        long start = System.currentTimeMillis();
        long chatId = Auth.Telegram.admin;
//        long chatId = 808560264;
        List<Message> messages = new ArrayList<>();
        for (int count = 1; count <= 1; count++) {
            messages.add(new Message(String.valueOf(count)));
        }
        MessageBot messageBot = new MessageBot(Auth.Telegram.token);
        messageBot.send(messages, chatId);
        long end = System.currentTimeMillis();
        System.out.println("it took " + (end - start) + " ms");
    }

    @GetMapping(path = "admin/test-broadcast")
    public void save() {

        String updateMessage = "[공지] %0A" +
                "채용정보 알리미 서비스 업데이트 이전, 메시지 속도 개선을 위한 점검을 진행합니다. %0A" +
                "점검 시간: 금일 오전 9 ~ 10시, %0A" +
                "화요일 오후 8 ~ 9시. %0A" +
                "감사합니다. 좋은 하루 보내세요:)";

        long start = System.currentTimeMillis();
        String demo1 = "본 메시지는 속도 측정을 위해 생성된 메시지입니다";
        String demo2 = "사용자에 따라 받는 메시지의 순서가 다를 수 있습니다";
        String demo3 = "본 메시지는 속도 측정을 위해 생성된 메시지 더미의 일부입니다";
        String demo4 = "사용자에 따라 수신하는 메시지의 순서가 다를 수 있습니다";

        MessageBot bot = new MessageBot(Auth.Telegram.token);
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(demo1));
        messages.add(new Message(demo2));
        messages.add(new Message(demo3));
        messages.add(new Message(demo4));
        bot.send(messages, Auth.Telegram.admin);

//        List<User> users = userRepository.findAll();
//        users.stream()
//                .map(User::userId)
//                .map(userId->{
//                    MessageBot messageBot = new MessageBot(Auth.Telegram.token);
//
//                    List<Message> messages = new ArrayList<>();
//                    messages.add(new Message(demo1));
//                    messages.add(new Message(demo2));
//                    messages.add(new Message(demo3));
//                    messages.add(new Message(demo4));
//
////                    System.out.println(messages);
//                    messageBot.send(messages, userId);
//                    return null;
//                })
//                .collect(Collectors.toList());

        long end = System.currentTimeMillis();
        System.out.println("it took " + (end - start) + " ms");
    }
}
