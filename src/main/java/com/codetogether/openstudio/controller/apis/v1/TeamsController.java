package com.codetogether.openstudio.controller.apis.v1;

import com.codetogether.openstudio.dto.team.TeamSaveRequestDto;
import com.codetogether.openstudio.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Team API"})
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams/")
@RestController
public class TeamsController {

    private final TeamService teamService;

    @ApiOperation(value = "단일 팀 매칭 요청", notes = "하나의 팀을 생성하는 API입니다.")
    @PostMapping("")
    public Long save(
            @ApiParam(name = "팀 매칭 요청 정보", value = "서브젝트 이름, 유저 리스트", required = true)
            @RequestBody TeamSaveRequestDto requestDto) {
        return teamService.save(requestDto);
    }

    @ApiOperation(value = "팀 삭제", notes = "하나의 팀을 삭제하는 API입니다.")
    @DeleteMapping("/{id}")
    public Long delete(
            @ApiParam(name = "삭제할 team id", value = "Team id", required = true)
            @PathVariable Long id) {
        teamService.delete(id);
        return id;
    }

    @ApiOperation(value = "각각의 풀에 대한 팀 매칭 요청", notes = "각각의 매칭 풀에 예약한 유저들을 매칭시키는 API입니다.")
    @PostMapping("/match")
    public String match() {
        teamService.matchAllReservationsOfPools();
        return "ok";
    }
}
