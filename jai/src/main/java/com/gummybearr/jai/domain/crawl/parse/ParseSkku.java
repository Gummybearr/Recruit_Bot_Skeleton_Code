package com.gummybearr.jai.domain.crawl.parse;

import com.gummybearr.jai.domain.recruitment.Recruitment;
import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParseSkku implements ParseStrategy {

    public static final int PARSE_COUNT = 25;

    @Override
    public List<Recruitment> parse(Document document) {
        Elements roughData = roughData(document);
        return IntStream.range(1, roughData.size())
                .mapToObj(idx -> {
                    Elements parsedData = roughData.get(idx).select("td");
                    String company = parsedData.get(1).text();
                    String content = parsedData.get(2).text();
                    String deadline = parsedData.get(3).text();
                    return new Recruitment(company, content, deadline, parsedDeadline(deadline));
                })
                .collect(Collectors.toList());
    }

    public static ParseStrategy of() {
        return new ParseSkku();
    }

    private Elements roughData(Document document) {
        return document.body().select("table").select("tr");
    }

    @SneakyThrows
    private static Long parsedDeadline(String deadline) {
        if (!isNumber(deadline)) {
            return Long.MAX_VALUE;
        }
        return parsedNumber(deadline);
    }

    private static boolean isNumber(String string) {
        return Pattern.matches("[0-9. ]+", string);
    }

    private static Long parsedNumber(String string) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = dateFormat.parse(string);
        return date.getTime() + ONE_DAY - SIX_HOURS;
    }
}
