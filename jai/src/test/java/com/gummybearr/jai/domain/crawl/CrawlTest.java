package com.gummybearr.jai.domain.crawl;

import com.gummybearr.jai.constants.Auth.SKKU;
import com.gummybearr.jai.constants.Urls;
import com.gummybearr.jai.domain.auth.Auth;
import com.gummybearr.jai.domain.crawl.parse.ParseJobKorea;
import com.gummybearr.jai.domain.crawl.parse.ParseSkku;
import com.gummybearr.jai.domain.recruitment.Recruitment;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.gummybearr.jai.constants.Auth.JobKorea;
import static org.assertj.core.api.Assertions.assertThat;

class CrawlTest {

    private Crawl crawl;

    @BeforeEach
    void setup() {
        this.crawl = new Crawl();
    }

    @Test
    @DisplayName("학교 사이트의 채용정보 html을 가져올 수 있다")
    void skkuHtmlSuccessful() throws IOException {
        Map<String, String> cookies = new Auth().cookies(Urls.SKKU.loginUrl, SKKU.of());
        Document document = crawl.htmlBody(Urls.SKKU.dataUrlPageOne, cookies);
        assertThat(document.location()).isEqualTo(Urls.SKKU.dataUrlPageOne);
    }

    @Test
    @DisplayName("잡 코리아의 채용정보 html을 가져올 수 있다")
    void jobKoreaHtmlSuccessful() throws IOException {
        Map<String, String> cookies = new Auth().cookies(Urls.JobKorea.loginUrl, JobKorea.of());
        Document document = crawl.htmlBody(Urls.JobKorea.dataUrl, cookies);
        assertThat(document.location()).isEqualTo(Urls.JobKorea.dataUrl);
    }

    @Test
    @DisplayName("학교 사이트의 채용정보를 파싱할 수 있다")
    void parseSkkuRecruit() throws IOException {
        Map<String, String> cookies = new Auth().cookies(Urls.SKKU.loginUrl, SKKU.of());
        Document document = crawl.htmlBody(Urls.SKKU.dataUrlPageOne, cookies);
        List<Recruitment> recruitments = crawl.parse(document, new ParseSkku());
        assertThat(recruitments.size()).isEqualTo(ParseSkku.PARSE_COUNT);
    }

    @Test
    @DisplayName("잡코리아의 채용정보를 파싱할 수 있다")
    void parseJobKoreaRecruit() throws IOException {
        Map<String, String> cookies = new Auth().cookies(Urls.JobKorea.loginUrl, JobKorea.of());
        Document document = crawl.htmlBody(Urls.JobKorea.dataUrl, cookies);
        List<Recruitment> recruitments = crawl.parse(document, new ParseJobKorea());
        assertThat(recruitments.size()).isEqualTo(ParseJobKorea.PARSE_COUNT);
    }

}
