package com.gummybearr.jai.domain.recruitment;

import com.gummybearr.jai.domain.user.User;
import com.gummybearr.jai.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    @GetMapping(path = "api-test")
    public void tmp() {
        for(int count = 0;count<100;count++){

        }
//        User user = new User(1L);
//        userRepository.save(user);

//        Recruitment recruitment = new Recruitment("company", UUID.randomUUID().toString(), "deadline", 1L);
//        recruitmentRepository.save(recruitment);
    }
}
