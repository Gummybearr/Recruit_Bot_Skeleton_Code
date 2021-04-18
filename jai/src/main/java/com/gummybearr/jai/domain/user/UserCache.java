package com.gummybearr.jai.domain.user;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserCache extends LinkedHashMap<Long, User> {
    private static final int CACHE_SIZE = 100;

    public UserCache() {
        super(CACHE_SIZE, 0.75f, true);
    }

    public User get(long chatId) {
        if (super.get(chatId) != null) {
            return super.get(chatId);
        }
        return null;
    }

    public void put(User user) {
        super.put(user.chatId(), user);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Long, User> eldest) {
        return this.size() > CACHE_SIZE;
    }
}
