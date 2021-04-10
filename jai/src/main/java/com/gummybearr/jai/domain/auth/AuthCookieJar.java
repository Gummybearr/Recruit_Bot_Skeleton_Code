package com.gummybearr.jai.domain.auth;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthCookieJar implements CookieJar {
    private List<Cookie> cookies;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if(cookies != null){
            return cookies;
        }
        return new ArrayList<>();
    }

    public Map<String, String> cookie(){
        return cookies.stream()
                .filter(cookie->!cookie.value().isEmpty())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toMap(Cookie::name, Cookie::value));
    }
}
