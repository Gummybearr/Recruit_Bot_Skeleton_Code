package com.gummybearr.jai.domain.admin;

import com.gummybearr.jai.domain.crawl.CrawlService;
import com.gummybearr.jai.domain.message.Message;
import com.gummybearr.jai.domain.recruitment.Recruitment;
import com.gummybearr.jai.domain.recruitment.RecruitmentRepository;
import com.gummybearr.jai.domain.recruitment.Recruitments;
import com.gummybearr.jai.domain.telegramBot.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final CrawlService crawlService;
    private final BotService botService;

    private final RecruitmentRepository recruitmentRepository;

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
        System.out.println(recruitmentRepository.findRecruitmentLike("네이버"));
    }

}
