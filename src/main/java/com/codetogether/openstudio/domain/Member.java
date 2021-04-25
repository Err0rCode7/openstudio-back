package com.codetogether.openstudio.domain;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(String picture) {
        this.picture = picture;

        return this;
    }

    public Member update(MemberUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.picture = requestDto.getPicture();
        this.role = requestDto.getRole();

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
