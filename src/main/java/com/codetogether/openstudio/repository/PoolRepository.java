package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Pool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PoolRepository extends JpaRepository<Pool, Long> {
    @Query("SELECT p FROM Pool p " +
            "JOIN FETCH p.subject " +
            "WHERE p.closedAt > :#{#currentTime} " +
                "AND " +
                "p.createdAt < :#{#currentTime}")
    List<Pool> findByDateBetween(@Param("currentTime") LocalDateTime date);

    @Query("SELECT p FROM Pool p " +
            "JOIN FETCH p.subject s " +
            "WHERE p.closedAt > :#{#currentTime} " +
            "AND " +
            "p.createdAt < :#{#currentTime} " +
            "AND " +
            "s.name = :#{#subjectName}")
    List<Pool> findBySubjectNameAndDateBetween(@Param("currentTime") LocalDateTime date,
                                 @Param("subjectName") String name);

    @Query("SELECT p FROM Pool p " +
            "JOIN FETCH p.subject " +
            "ORDER BY p.id DESC")
    List<Pool> findAllDesc();

    @Query(value = "SELECT p FROM Pool p " +
            "JOIN FETCH p.subject " +
            "ORDER BY p.id DESC",
            countQuery = "SELECT count(p) FROM Pool p INNER JOIN p.subject")
    Page<Pool> findAllDesc(Pageable pageable);
}
