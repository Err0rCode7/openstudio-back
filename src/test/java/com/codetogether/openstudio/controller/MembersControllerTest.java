package com.codetogether.openstudio.controller;

import com.codetogether.openstudio.config.auth.Role;
import com.codetogether.openstudio.config.auth.SecurityConfig;
import com.codetogether.openstudio.controller.apis.v1.MembersController;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @DisplayName("?????? ?????? - ???????????? DTO")
    public void ????????????_????????????_?????????() throws Exception {


        // when
        // admin??? post method??? /api/vi/members uri??? ???????????????
        MemberSaveRequestDto requestDto = new MemberSaveRequestDto("member1", "email", "picture", Role.USER);

        // then
        // 200??? ??????????????????..
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/members/")
                    .with(csrf())
                        // ????????? csrf ????????? ??????????????? @AutoConfigureMockMvc(addFilters = false)??? ???????????? ????????????
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
    @DisplayName("?????? ?????? - ???????????? DTO")
    public void ????????????_????????????_?????????() throws Exception {

        // when
        // ???????????? 0??? id??? ?????? ????????? ????????? ?????? ???????????? ???
        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("member2", "email2", "picture2", Role.GUEST);
        // then
        // status code 200??? ??????????????????.
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