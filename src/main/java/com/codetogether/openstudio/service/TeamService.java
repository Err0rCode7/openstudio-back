package com.codetogether.openstudio.service;

import com.codetogether.openstudio.domain.Member;
import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Team;
import com.codetogether.openstudio.domain.TeamMember;
import com.codetogether.openstudio.dto.team.TeamListResponseDto;
import com.codetogether.openstudio.dto.team.TeamSaveRequestDto;
import com.codetogether.openstudio.repository.MemberRepository;
import com.codetogether.openstudio.repository.PoolRepository;
import com.codetogether.openstudio.repository.TeamMemberRepository;
import com.codetogether.openstudio.repository.TeamRepository;
import com.codetogether.openstudio.util.CollectorUtils;
import com.codetogether.openstudio.util.DividerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final PoolRepository poolRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional(readOnly = true)
    public List<TeamListResponseDto> findAllDesc() {
        return teamRepository.findAllDesc().stream()
                .map(team -> {
                    team.update(teamMemberRepository.findByTeamId(team.getId()).stream()
                            .collect(Collectors.toList()));
                    return new TeamListResponseDto(team);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(TeamSaveRequestDto requestDto) {
        List<Member> Members = requestDto.getUserNames().stream()
                .map((userName) -> {
                    return memberRepository.findByName(userName)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "존재하지 않는 유저의 이름입니다. userName = " + userName));
                }).collect(Collectors.toList());
        List<Pool> pools = poolRepository.
                findBySubjectNameAndDateBetween(LocalDateTime.now(), requestDto.getSubjectName());
        if (pools.size() != 1) {
            throw new IllegalArgumentException(
                    "존재하지 않는 서브젝트 이름입니다. subjectName = " +
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 번호입니다. id =" + id));
        teamRepository.deleteById(id);
    }

    @Transactional
    public void matchAllReservationsOfPools() {
        poolRepository.findByDateBetween(LocalDateTime.now()).stream()
                .map(DividerUtils::getTeamList)
                .forEach(teams -> teams.stream()
                        .forEach(teamRepository::save));
    }

}
