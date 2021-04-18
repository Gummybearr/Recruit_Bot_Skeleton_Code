package com.gummybearr.jai.service;

import com.gummybearr.jai.constants.Auth.SKKU;
import com.gummybearr.jai.constants.Urls;
import com.gummybearr.jai.domain.crawl.Crawl;
import com.gummybearr.jai.domain.crawl.auth.Auth;
import com.gummybearr.jai.domain.crawl.parse.ParseJobKorea;
import com.gummybearr.jai.domain.crawl.parse.ParseSkku;
import com.gummybearr.jai.domain.crawl.parse.ParseStrategy;
import com.gummybearr.jai.domain.recruitment.Recruitment;
import com.gummybearr.jai.domain.recruitment.RecruitmentRepository;
import com.gummybearr.jai.domain.recruitment.Recruitments;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gummybearr.jai.constants.Auth.JobKorea;

@Service
@RequiredArgsConstructor
public class CrawlService {

    private final Auth auth = new Auth();
    private final Crawl crawl = new Crawl();

    private final RecruitmentRepository recruitmentRepository;

    public Recruitments recruitments() throws IOException {
        List<Recruitment> recruitmentsSkkuPageOne = crawlAndParse(auth.cookies(Urls.SKKU.loginUrl, SKKU.of()), Urls.SKKU.dataUrlPageOne, ParseSkku.of());
        List<Recruitment> recruitmentsSkkuPageTwo = crawlAndParse(auth.cookies(Urls.SKKU.loginUrl, SKKU.of()), Urls.SKKU.dataUrlPageTwo, ParseSkku.of());
        List<Recruitment> recruitmentsJobKorea = crawlAndParse(auth.cookies(Urls.JobKorea.loginUrl, JobKorea.of()), Urls.JobKorea.dataUrl, ParseJobKorea.of());

        recruitmentsSkkuPageOne.addAll(recruitmentsSkkuPageTwo);
        recruitmentsSkkuPageOne.addAll(recruitmentsJobKorea);

        recruitmentsSkkuPageOne = uniqueRecruitments(recruitmentsSkkuPageOne);
        recruitmentRepository.saveAll(recruitmentsSkkuPageOne);
        return new Recruitments(recruitmentsSkkuPageOne);
    }

    private List<Recruitment> crawlAndParse(Map<String, String> cookies, String dataUrl, ParseStrategy parseStrategy) throws IOException {
        Document document = crawl.htmlBody(dataUrl, cookies);
        return crawl.parse(document, parseStrategy);
    }

    public List<Recruitment> uniqueRecruitments(List<Recruitment> recruitments) {
        List<Long> hashValues = recruitments.parallelStream()
                .map(Recruitment::hashedValue)
                .collect(Collectors.toList());

        List<Long> dupRecruitments = recruitmentRepository.findByHashedValueIn(hashValues)
                .parallelStream()
                .map(Recruitment::hashedValue)
                .collect(Collectors.toList());

        return recruitments.parallelStream()
                .filter(recruitment -> !dupRecruitments.contains(recruitment.hashedValue()))
                .collect(Collectors.toList());
    }

}
