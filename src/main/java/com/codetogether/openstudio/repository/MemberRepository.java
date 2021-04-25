package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m " +
            "ORDER BY m.id DESC")
    List<Member> findAllDesc();
    @Query("SELECT m FROM Member m " +
            "ORDER BY m.id DESC")
    Page<Member> findAllDesc(Pageable pageable);
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    @Query("SELECT count(m) FROM Member m " +
            "WHERE m.name = :#{#name} " +
            "OR m.email = :#{#email}")
    Long countByNameOrEmail(@Param("name") String name, @Param("email") String email);
}
