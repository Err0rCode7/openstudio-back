package com.codetogether.openstudio.domain;

import com.codetogether.openstudio.config.auth.Role;
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
    private String intraId;

    @Column(unique = true)
    private String email;
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String intraId, String email, String picture, Role role) {
        this.intraId = intraId;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(String picture) {
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
