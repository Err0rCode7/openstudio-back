package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Team;
import com.codetogether.openstudio.dto.team.TeamListResponseDto;
import com.codetogether.openstudio.dto.team.TeamSaveRequestDto;
import com.codetogether.openstudio.repository.*;
import com.codetogether.openstudio.util.DividerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PoolRepository poolRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MailService mailService;

    @Transactional(readOnly = true)
    public List<TeamListResponseDto> findAllDesc() {
        return teamRepository.findAllDesc().stream()
                .map(team -> {
                    team.update(teamMemberRepository.findByTeamId(team.getId()));
                    return new TeamListResponseDto(team);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<TeamListResponseDto> findAllDesc(Pageable pageable) {
        Page<Team> teamPage = teamRepository.findAllDesc(pageable);
        int totalElements = (int) teamPage.getTotalElements();
        return new PageImpl<>(teamPage
                .getContent().stream()
                .map(TeamListResponseDto::new)
                .collect(Collectors.toList()), pageable, totalElements);
    }

    @Transactional
    public Long save(TeamSaveRequestDto requestDto) {
        if (requestDto.getUserNames().size() < 3 || requestDto.getUserNames().size() > 4) {
           throw new IllegalArgumentException("??? ????????? ????????? ????????? ????????? 3-4?????? ????????????.");
        }
        if ( isDifferentNames(requestDto.getUserNames()) == false) {
            throw new IllegalArgumentException("????????? ????????? ????????? ?????? ?????? ??? ??? ????????????.");
        }
        List<Member> Members = requestDto.getUserNames().stream()
                .map((userName) -> {
                    return memberRepository.findByName(userName)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "???????????? ?????? ????????? ???????????????. userName = " + userName));
                }).collect(Collectors.toList());
        List<Pool> pools = poolRepository.
                findBySubjectNameAndDateBetween(LocalDateTime.now(), requestDto.getSubjectName());
        if (pools.size() != 1) {
            throw new IllegalArgumentException(
                    "???????????? ?????? ???????????? ???????????????. subjectName = " +
                    requestDto.getSubjectName());
        } else {
            return teamRepository.save(new Team(
                    pools.get(0),
                    LocalDateTime.now().plusDays(14),
                    Members))
                        .getId();
        }
    }

    @Transactional
    public void delete(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("???????????? ?????? ??? ???????????????. id =" + id));
        teamRepository.deleteById(id);
    }

    @Transactional
    public void matchAllReservationsOfPools() {
        DividerUtils dividerUtils = new DividerUtils();
        Stream<List<Team>> teamsStream = poolRepository.findByDateBetween(LocalDateTime.now()).stream()
                .map(pool -> dividerUtils.getTeamList(pool));
        teamsStream
            .forEach(teams -> {
                teams.stream()
                    .forEach(team -> {
                        teamRepository.save(team);
                        mailService.sendSurveyForm(team);
                    });        
            });
    }

    public static boolean isDifferentNames(List<String> names) {
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            for (int j = 0; j < names.size(); j++) {
                String target = names.get(j);
                if (i != j && name.equals(target)) {
                    return false;
                }
            }
        }
        return true;
    }
}
