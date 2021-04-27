package com.codetogether.openstudio.contorller;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.config.auth.SecurityConfig;
import com.codetogether.openstudio.contorller.admin.MembersAdminController;
import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.dto.auth.SessionUser;
import com.codetogether.openstudio.dto.member.MemberListResponseDto;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MembersController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = SecurityConfig.class)
    }
)
@AutoConfigureMockMvc(addFilters = false)
public class MembersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberRepository memberRepository;
    @MockBean
    MemberService memberService;

    @WithMockUser(roles = "ADMIN")
    @Test
    @DisplayName("멤버 저장 - 정상적인 DTO")
    public void 멤버저장_정상적인_케이스() throws Exception {


        // when
        // admin이 post method로 /api/vi/members uri에 요청했을때
        MemberSaveRequestDto requestDto = new MemberSaveRequestDto("member1", "email", "picture", Role.USER);

        // then
        // 200을 반환해야한다..
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/members")
                    .with(csrf())
                        // 이렇게 csrf 토큰을 넣어주거나 @AutoConfigureMockMvc(addFilters = false)를 사용하여 필터제거
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    @DisplayName("멤버 수정 - 정상적인 DTO")
    public void 멤버수정_정상적인_케이스() throws Exception {

        // when
        // 어드민이 0번 id를 갖은 유저의 내용을 수정 요청했을 때
        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("member2", "email2", "picture2", Role.GUEST);
        // then
        // status code 200을 반환해야한다.
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/v1/members/{id}", "0")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
    }
}