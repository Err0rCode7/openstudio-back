package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.dto.member.MemberListResponseDto;
import com.codetogether.openstudio.dto.member.MemberResponseDto;
import com.codetogether.openstudio.dto.member.MemberSaveRequestDto;
import com.codetogether.openstudio.dto.member.MemberUpdateRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponseDto findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 갖는 유저가 없습니다. email = " + email));
        return new MemberResponseDto(member);
    }

    @Transactional(readOnly = true)
    public List<MemberListResponseDto> findAllDesc() {
        return memberRepository.findAllDesc().stream()
                .map(MemberListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 갖는 유저가 없습니다. id = " + id));
        return new MemberResponseDto(member);
    }

    @Transactional
    public Long save(MemberSaveRequestDto requestDto) {
        memberRepository.findByEmail(requestDto.getEmail())
                .ifPresent(member -> {
                    throw new IllegalArgumentException("해당 이메일로는 계정을 생성할 수 없습니다.");
                });
        if (requestDto == null || hasZeroString(requestDto)) {
            throw new IllegalArgumentException("가입을 요청한 유저의 정보에 빈 문자열이 존재합니다.");
        }
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 갖는 유저가 없습니다. id = " + id));
        Long foundMemberCount = memberRepository.countByNameOrEmail(requestDto.getName(), requestDto.getEmail());
        if (foundMemberCount != 1L) {
            throw new IllegalArgumentException("해당 이름 또는 이메일을 갖은 유저가 존재합니다. name = " + requestDto.getName() + ", email = " + requestDto.getEmail());
        }
        member.update(requestDto.getPicture());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 갖는 유저가 없습니다. id = " + id));
        memberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<MemberListResponseDto> findAllDesc(Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAllDesc(pageable);
        int totalElements = (int) memberPage.getTotalElements();
        return new PageImpl<>(memberPage
        .getContent().stream()
        .map(MemberListResponseDto::new)
        .collect(Collectors.toList()), pageable, totalElements);
    }

    public static boolean hasZeroString(MemberSaveRequestDto dto) {
        System.out.println("name = " + dto.getName());
        if (dto.getName().length() == 0 || dto.getEmail().length() == 0 || dto.getPicture().length() == 0 ||
                dto.getRole() == null || dto.getRole().name().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
