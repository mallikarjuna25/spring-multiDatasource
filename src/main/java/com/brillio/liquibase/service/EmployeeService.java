package com.brillio.liquibase.service;

import com.brillio.liquibase.exception.UserExistsException;
import com.brillio.liquibase.exception.UserNotFoundException;
import com.brillio.liquibase.model.employee.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> allProfiles();
    Employee addProfile(Employee employee) throws UserExistsException;
    Boolean updateProfile(Employee employee) throws UserNotFoundException;
    Employee deleteProfile(String email) throws UserNotFoundException;


}
