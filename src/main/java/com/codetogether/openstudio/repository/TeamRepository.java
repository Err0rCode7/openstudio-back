package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t from Team t " +
            "JOIN FETCH t.pool p " +
            "JOIN FETCH p.subject " +
            "ORDER BY t.id DESC")
    List<Team> findAllDesc();
}
