package com.brillio.liquibase.service;

import com.brillio.liquibase.exception.UserExistsException;
import com.brillio.liquibase.exception.UserNotFoundException;
import com.brillio.liquibase.exception.UserNullPointerException;
import com.brillio.liquibase.model.employee.Employee;
import com.brillio.liquibase.repository.employeeRepo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepo repo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo repo) {
        this.repo = repo;
    }

    @Override
    public Employee addProfile(Employee employee) throws UserExistsException {
        if(repo.existsByEmail(employee.getEmail())){
            throw new UserExistsException("Profile Already exists With Email");
        } else if(employee.getEmail()== null || employee.getEmail().isEmpty() ){
            throw new UserNullPointerException("email is required to create a profile");
        }
        Employee savedProfile = repo.save(employee);
        return savedProfile;

    }

    @Override
    public Boolean updateProfile(Employee employee) throws UserNotFoundException {
        Optional<Employee> byEmail = repo.findByEmail(employee.getEmail());
        if(byEmail.isPresent()){
            Employee employee1 = repo.findByEmail(employee.getEmail()).get();
            employee1.setName(employee.getName());
            employee1.setEmail(employee.getEmail());
            employee1.setGender(employee.getGender());
            employee1.setPhone(employee.getPhone());
            employee1.setTitle(employee.getTitle());
            employee1.setBonus(employee.getBonus());
            repo.save(employee1);
            return true;
        }
        else{
            throw new UserNotFoundException("Profile Not Found");
        }
    }

    @Override
    public Employee deleteProfile(String email) throws UserNotFoundException {
        if(repo.existsByEmail(email)){
            Employee employee = repo.deleteByEmail(email);
            return employee;
        }
        throw new UserNotFoundException("Profile Not Found");
    }


    @Override
    public List<Employee> allProfiles() {
        return repo.findAll();
    }

}
