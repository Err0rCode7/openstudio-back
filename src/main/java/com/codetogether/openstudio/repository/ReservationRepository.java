package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.pool p " +
            "JOIN FETCH p.subject s " +
            "JOIN FETCH r.member m " +
            "WHERE p.closedAt > :#{#currentTime} " +
            "AND " +
            "p.createdAt < :#{#currentTime}")
    List<Reservation> findByDateBetween(@Param("currentTime") LocalDateTime date);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.pool p " +
            "JOIN FETCH p.subject s " +
            "JOIN FETCH r.member m " +
            "WHERE p.id = :#{#pool_id}")
    List<Reservation> findByPoolId(@Param("pool_id") Long pool_id);

    @Query("SELECT COUNT(r.id) FROM Reservation r " +
            "JOIN r.pool p " +
            "WHERE p.closedAt > :#{#currentTime} " +
            "AND " +
            "p.createdAt < :#{#currentTime}")
    long countByDateBetween(@Param("currentTime") LocalDateTime date);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.pool p " +
            "JOIN FETCH r.member m " +
            "ORDER BY r.id DESC")
    List<Reservation> findAllDesc();

    @Query(value = "SELECT r FROM Reservation r " +
            "JOIN FETCH r.pool p " +
            "JOIN FETCH r.member m " +
            "ORDER BY r.id DESC",
            countQuery = "SELECT count(r) FROM Reservation r " +
                    "INNER JOIN r.pool p " +
                    "INNER JOIN r.member m")
    Page<Reservation> findAllDesc(Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.pool p " +
            "JOIN FETCH p.subject s " +
            "JOIN FETCH r.member m " +
            "WHERE p.closedAt > :#{#currentTime} " +
            "AND " +
            "p.createdAt < :#{#currentTime} " +
            "AND " +
            "m.name = :#{#memberName}")
    Optional<Reservation> findByMemberNameAndDateBetween(@Param("memberName") String memberName, @Param("currentTime") LocalDateTime date);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.pool p " +
            "JOIN FETCH p.subject s " +
            "JOIN FETCH r.member m " +
            "WHERE p.closedAt > :#{#currentTime} " +
            "AND " +
            "p.createdAt < :#{#currentTime} " +
            "AND " +
            "s.id = :#{#subjectId}")
    List<Reservation> findBySubjectIdAndDateBetween(
            @Param("subjectId") Long subjectId,
            @Param("currentTime") LocalDateTime date);

    int countByPoolId(Long poolId);
}
