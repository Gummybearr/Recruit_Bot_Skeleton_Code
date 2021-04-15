package com.gummybearr.jai.domain.crawl.auth;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Map;

public class Auth {

    public Map<String, String> cookies(String url, Map<String, String> data) throws IOException {
        AuthCookieJar cookieJar = new AuthCookieJar();
        OkHttpClient httpClient = httpClient(cookieJar);
        RequestBody body = body(data);
        Request request = request(url, body);

        httpClient.newCall(request).execute();
        return cookieJar.cookie();
    }

    private OkHttpClient httpClient(AuthCookieJar cookieJar) {
        return new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }

    private RequestBody body(Map<String, String> data) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    private Request request(String url, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

}
