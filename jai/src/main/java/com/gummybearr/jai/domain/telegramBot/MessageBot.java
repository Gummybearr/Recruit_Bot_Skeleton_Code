package com.gummybearr.jai.domain.telegramBot;

import com.gummybearr.jai.domain.message.Message;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

public class MessageBot {
    private static final String URL_STRING = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
    private static final int CONNECTION_TIMEOUT_LIMIT = 10_000;

    private final String apiToken;

    public MessageBot(String apiToken) {
        this.apiToken = apiToken;
    }

    public void send(List<Message> messages, Long chatId) {
        messages.parallelStream()
                .map(message -> send(message, chatId))
                .collect(Collectors.toList());
    }

    private String send(Message message, Long chatId) {
        try {
            String urlString = String.format(URL_STRING, this.apiToken, chatId, message);
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(CONNECTION_TIMEOUT_LIMIT);
            InputStream is = new BufferedInputStream(conn.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();
        } catch (IOException e) {
            AdminBot.send(new Message(e.getMessage()));
            return null;
        }
    }

}
