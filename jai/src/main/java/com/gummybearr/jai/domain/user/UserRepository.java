package com.gummybearr.jai.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByChatIdIn(List<Long> userIds);

    Optional<User> findFirstByChatId(Long userId);
}
