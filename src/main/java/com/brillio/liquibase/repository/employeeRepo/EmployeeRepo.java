package com.brillio.liquibase.repository.employeeRepo;

import com.brillio.liquibase.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);
    @Transactional
    @Modifying
    Employee deleteByEmail(String email);
}
