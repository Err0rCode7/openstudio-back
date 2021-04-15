package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PoolRepository extends JpaRepository<Pool, Long> {
    @Query("SELECT p FROM Pool p " +
            "JOIN FETCH p.subject " +
            "WHERE p.closedAt > :#{#currentTime} " +
                "AND " +
                "p.createdAt < :#{#currentTime}")
    List<Pool> findByDateBetween(@Param("currentTime") LocalDateTime date);

    @Query("SELECT p FROM Pool p " +
            "JOIN FETCH p.subject " +
            "ORDER BY p.id DESC")
    List<Pool> findAllDesc();
}
