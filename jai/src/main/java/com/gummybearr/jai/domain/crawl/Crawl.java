package com.gummybearr.jai.domain.crawl;

import com.gummybearr.jai.domain.crawl.parse.ParseStrategy;
import com.gummybearr.jai.domain.recruitment.Recruitment;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Crawl {

    public static final String CRON_CRAWL_TERM = "0 13 * * 1-5";

    public Document htmlBody(String url, Map<String, String> cookies) throws IOException {
        Connection connection = Jsoup.connect(url);
        connection.cookies(cookies);
        return connection.post();
    }

    public List<Recruitment> parse(Document document, ParseStrategy parseStrategy) {
        return parseStrategy.parse(document);
    }

}
