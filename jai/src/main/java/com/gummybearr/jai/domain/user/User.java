package com.gummybearr.jai.domain.user;

import com.gummybearr.jai.domain.recruitment.Recruitment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(columnList = "chatId")})
@Getter
@ToString
public class User {

    private static final String DELIMITER = " ";
    public static final String COLOR_LIST_CONCAT_FORMAT = "%s %s";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long chatId;
    private String blackList;
    private String whiteList;

    public User(Long chatId) {
        this.chatId = chatId;
        this.blackList = "";
        this.whiteList = "";
    }

    public User addBlackList(String string) {
        this.blackList = String.format(COLOR_LIST_CONCAT_FORMAT, delBlackList(string).blackList, string).trim();
        return this;
    }

    public User delBlackList(String string) {
        this.blackList = Arrays.stream(blackList.split(DELIMITER))
                .filter(v -> !v.contains(string))
                .filter(v -> !string.contains(v))
                .collect(Collectors.joining(DELIMITER));
        return this;
    }

    public User addWhiteList(String string) {
        this.whiteList = String.format(COLOR_LIST_CONCAT_FORMAT, delWhiteList(string).whiteList, string).trim();
        return this;
    }

    public User delWhiteList(String string) {
        this.whiteList = Arrays.stream(whiteList.split(DELIMITER))
                .filter(v -> !v.contains(string))
                .filter(v -> !string.contains(v))
                .collect(Collectors.joining(DELIMITER));
        return this;
    }

    public boolean filterBlackList(Recruitment recruitments) {
        if (blackList.trim().isBlank()) {
            return true;
        }
        List<String> blackLists = Arrays.stream(blackList.split(DELIMITER))
                .collect(Collectors.toList());
        String flatString = recruitments.flatString();
        return blackLists.parallelStream().noneMatch(flatString::contains);
    }

    public boolean filterWhiteList(Recruitment recruitments) {
        if (whiteList.trim().isBlank()) {
            return true;
        }
        List<String> blackLists = Arrays.stream(whiteList.split(DELIMITER))
                .collect(Collectors.toList());
        String flatString = recruitments.flatString();
        return blackLists.parallelStream().anyMatch(flatString::contains);
    }
}
