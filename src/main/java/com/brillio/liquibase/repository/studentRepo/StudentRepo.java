package com.brillio.liquibase.repository.studentRepo;

import com.brillio.liquibase.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);
    @Transactional
    @Modifying
    Student deleteByEmail(String email);
}
