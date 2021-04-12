package com.codetogether.openstudio.dto.auth;

import com.codetogether.openstudio.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String intraId;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.intraId = member.getIntraId();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
