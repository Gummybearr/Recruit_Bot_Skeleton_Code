package com.gummybearr.jai.domain.crawl.parse;

import com.gummybearr.jai.domain.recruitment.Recruitment;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParseJobKorea implements ParseStrategy {

    public static final int PARSE_COUNT = 50;

    @Override
    public List<Recruitment> parse(Document document) {
        Elements roughData = roughData(document);
        return roughData.stream()
                .map(element -> {
                    String company = company(element);
                    String content = content(element);
                    String deadline = deadline(element);
                    return new Recruitment(company, content, deadline, parsedDeadline(deadline));
                })
                .collect(Collectors.toList());
    }

    public static ParseStrategy of() {
        return new ParseJobKorea();
    }

    private Elements roughData(Document document) {
        return document.body()
                .select(".rankList")
                .get(0)
                .select("li");
    }

    private String company(Element element) {
        return element.select(".coLink")
                .get(0)
                .select("b")
                .get(0)
                .text();
    }

    private String content(Element element) {
        return element.select(".link")
                .get(0)
                .text();
    }

    private String deadline(Element element) {
        return element.select(".day")
                .get(0)
                .text();
    }

    @SneakyThrows
    private Long parsedDeadline(String deadline) {
        if (!isNumberWithDay(deadline)) {
            return parsedDay(deadline);
        }
        return parsedNumberWithDay(deadline);
    }

    private static boolean isNumberWithDay(String string) {
        return Pattern.matches("[~][0-9/]+[(.)]", string);
    }

    private Long parsedDay(String string) {
        if (string.startsWith("오늘")) {
            return System.currentTimeMillis();
        }
        if (string.startsWith("내일")) {
            return System.currentTimeMillis() + ONE_DAY + ONE_DAY - SIX_HOURS;
        }
        return Long.MAX_VALUE;
    }

    private Long parsedNumberWithDay(String string) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = dateFormat.parse("2021/" + string.substring(1, string.length() - 3));
        return date.getTime() + ONE_DAY - SIX_HOURS;
    }
}
