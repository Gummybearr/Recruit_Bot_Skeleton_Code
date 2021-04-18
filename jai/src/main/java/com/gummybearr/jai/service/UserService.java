package com.gummybearr.jai.service;

import com.gummybearr.jai.domain.user.User;
import com.gummybearr.jai.domain.user.UserCache;
import com.gummybearr.jai.domain.user.UserRepository;
import com.gummybearr.jai.domain.userMessage.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCache userCache;
    private final UserRepository userRepository;

    public User fetchUser(UserMessage userMessage) {
        User user = userCache.get(userMessage.getChatId());
        if (user != null) {
            userCache.put(userCache.get(userMessage.getChatId()));
            return user;
        }
        user = userRepository.findFirstByChatId(userMessage.getChatId()).orElse(null);
        if (user == null) {
            user = userRepository.save(new User(userMessage.getChatId()));
        }
        userCache.put(user);
        return user;
    }

    public User putUser(User user) {
        userCache.put(user);
        userRepository.save(user);
        return userCache.get(user);
    }
}
