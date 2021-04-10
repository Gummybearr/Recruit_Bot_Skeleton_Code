package com.gummybearr.jai.domain.crawl.parse;

import com.gummybearr.jai.domain.recruitment.Recruitment;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public interface ParseStrategy {
    int SIX_HOURS = 1000 * 60 * 60 * 6;
    int ONE_DAY = SIX_HOURS * 4;

    List<Recruitment> parse(Document document);

}
