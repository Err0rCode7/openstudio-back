package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s ORDER BY s.id DESC")
    List<Subject> findAllDesc();

    @Query("SELECT s FROM Subject s ORDER BY s.id DESC")
    Page<Subject> findAllDesc(Pageable pageable);

    @Query("SELECT s FROM Subject s ORDER BY s.circle ASC")
    List<Subject> findAllOrderByCircleASC();

    Optional<Subject> findByName(String name);
}
