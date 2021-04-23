package com.codetogether.openstudio.dto.member;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSaveRequestDto {
    private String name;
    private String email;
    private String picture;
    private Role role;

    public MemberSaveRequestDto(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build();
    }
}
