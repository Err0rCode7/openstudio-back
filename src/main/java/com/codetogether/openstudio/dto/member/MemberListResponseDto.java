package com.codetogether.openstudio.dto.member;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberListResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;
    private LocalDateTime createdAt;


    public MemberListResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.role = entity.getRole();
        this.createdAt = entity.getCreatedAt();
    }
}
