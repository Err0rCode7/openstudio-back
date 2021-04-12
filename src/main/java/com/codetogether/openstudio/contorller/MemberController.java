package com.codetogether.openstudio.contorller;

import com.codetogether.openstudio.config.auth.LoginUser;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public Member getMember(@LoginUser SessionUser user) {
        if (user != null) {
            System.out.println("user.getEmail() = " + user.getEmail());
            Optional<Member> newMember = memberRepository.findByEmail(user.getEmail());
            if (newMember.isPresent()) {
                return newMember.get();
            }
        }
        return new Member();
    }
}
