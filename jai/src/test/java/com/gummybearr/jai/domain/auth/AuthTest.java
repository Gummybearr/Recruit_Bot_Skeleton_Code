package com.gummybearr.jai.domain.auth;

import com.gummybearr.jai.constants.Urls;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static com.gummybearr.jai.constants.Auth.*;

class AuthTest {

    private Auth auth;

    @BeforeEach
    void setup() {
        this.auth = new Auth();
    }

    @Test
    @DisplayName("학교 사이트의 쿠키를 추출할 수 있다")
    void skkuLoginSuccessful() throws IOException {
        Map<String, String> map = auth.cookies(Urls.SKKU.loginUrl, SKKU.of());
        Assertions.assertThat(map.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("잡코리아의 쿠키를 추출할 수 있다")
    void jobKoreaLoginSuccessful() throws IOException {
        Map<String, String> map = auth.cookies(Urls.JobKorea.loginUrl, JobKorea.of());
        System.out.println(map);
        Assertions.assertThat(map.size()).isEqualTo(8);
    }

}
