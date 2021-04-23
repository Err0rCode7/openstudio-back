package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
