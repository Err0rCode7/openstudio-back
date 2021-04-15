package com.codetogether.openstudio.dto.member;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;
    private LocalDateTime createdAt;

    @Builder
    public MemberResponseDto(Long id, String name, String email, String picture, Role role, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.createdAt = createdAt;
    }

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.role = entity.getRole();
        this.createdAt = entity.getCreatedAt();
    }
}
