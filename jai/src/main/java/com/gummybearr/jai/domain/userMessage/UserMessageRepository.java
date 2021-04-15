package com.gummybearr.jai.domain.userMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
    List<UserMessage> findByMessageIdIn(@Param("messageIds") List<Long> messageIds);
}
