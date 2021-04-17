package com.codetogether.openstudio.contorller;

import com.codetogether.openstudio.dto.team.TeamSaveRequestDto;
import com.codetogether.openstudio.service.TeamService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
@RestController
public class TeamsController {

    private final TeamService teamService;

    @PostMapping("")
    public Long save(@RequestBody TeamSaveRequestDto requestDto) {
        return teamService.save(requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        teamService.delete(id);
        return id;
    }

    @GetMapping("")
    public String matchTest() {

        teamService.matchAllReservationsOfPools();
        return "ok";
    }
}
