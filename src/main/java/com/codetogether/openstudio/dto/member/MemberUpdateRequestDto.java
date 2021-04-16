package com.codetogether.openstudio.dto.member;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberUpdateRequestDto {
    private String name;
    private String email;
    private String picture;
    private Role role;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build();
    }
}
