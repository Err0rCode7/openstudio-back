package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t from Team t " +
            "JOIN FETCH t.pool p " +
            "JOIN FETCH p.subject " +
            "ORDER BY t.id DESC")
    List<Team> findAllDesc();

    @Query(value = "SELECT t from Team t " +
            "JOIN FETCH t.pool p " +
            "JOIN FETCH p.subject " +
            "ORDER BY t.id DESC",
            countQuery = "SELECT count(t) FROM Team t " +
                    "INNER JOIN t.pool p " +
                    "INNER JOIN p.subject s")
    Page<Team> findAllDesc(Pageable pageable);
}
