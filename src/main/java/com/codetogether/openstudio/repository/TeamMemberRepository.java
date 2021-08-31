package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    @Query("SELECT tm FROM TeamMember tm " +
            "JOIN FETCH tm.member m " +
            "JOIN FETCH tm.team t " +
            "WHERE t.id = :#{#teamId}")
    List<TeamMember> findByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT distinct tm FROM TeamMember tm " +
            "JOIN FETCH tm.member m " +
            "JOIN FETCH tm.team t " +
            "WHERE m.name = :#{#memberName}")
    List<TeamMember> findByMemberName(@Param("memberName") String name);
}
