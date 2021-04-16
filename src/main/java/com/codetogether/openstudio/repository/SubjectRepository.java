package com.codetogether.openstudio.repository;

import com.codetogether.openstudio.domain.Pool;
import com.codetogether.openstudio.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s ORDER BY s.id DESC")
    List<Subject> findAllDesc();

    @Query("SELECT s FROM Subject s ORDER BY s.circle ASC")
    List<Subject> findAllOrderByCircleASC();
}
