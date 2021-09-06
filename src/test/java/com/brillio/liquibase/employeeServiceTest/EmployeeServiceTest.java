package com.brillio.liquibase.employeeServiceTest;

import com.brillio.liquibase.exception.UserNotFoundException;
import com.brillio.liquibase.model.employee.Employee;
import com.brillio.liquibase.repository.employeeRepo.EmployeeRepo;
import com.brillio.liquibase.service.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;
    private Employee employee;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp(){
        employee=new Employee(1,"malli","male",12345555,"developer","malli@gmail.com",50000);
    }

    @AfterEach
    void tearDown(){
        employee=null;
    }

    @Test
    void givenEmployeeToSaveThenShouldReturnSavedEmployee() {
        when(employeeRepo.save(any())).thenReturn(employee);
        assertEquals(employee,employeeServiceImpl.addProfile(employee));
    }

    @Test
    void givenEmployeeToSaveThenShouldNotReturnSavedEmployee(){
        when(employeeRepo.save(any())).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class,()->employeeServiceImpl.addProfile(employee));
    }

    @Test
    void givenGetAllEmployeesThenShouldReturnListOfAllEmployees(){
        Employee employeeNew = new Employee(2,"arjun","male",88995555,"Analyst","arjun@gmail.com",6000);
        employeeServiceImpl.addProfile(employee);
        employeeServiceImpl.addProfile(employeeNew);

        when(employeeRepo.findAll()).thenReturn(employeeList);
        List<Employee> employeeListGet = (List<Employee>) employeeServiceImpl.allProfiles();
        assertEquals(employeeList,employeeListGet);
        verify(employeeRepo, times(1)).findAll();
    }

    @Test
    void givenEmployeeEmailToDeleteThenShouldReturnDeletedEmployee(){
        when(employeeRepo.existsByEmail(employee.getEmail())).thenReturn(true);
        when(employeeRepo.deleteByEmail(employee.getEmail())).thenReturn(employee);
//       Employee emp = employeeServiceImpl.deleteProfile(employee.getEmail());
        assertEquals(employee,employeeServiceImpl.deleteProfile(employee.getEmail()));
        verify(employeeRepo, times(1)).existsByEmail(anyString());
        verify(employeeRepo, times(1)).deleteByEmail(anyString());

    }

    @Test
    void givenEmployeeEmailToDeleteThenShould_Not_ReturnDeletedEmployee(){
        when(employeeRepo.existsByEmail(employee.getEmail())).thenReturn(false);
        assertThrows(UserNotFoundException.class,()->employeeServiceImpl.deleteProfile(employee.getEmail()));
        verify(employeeRepo, times(1)).existsByEmail(anyString());
        verify(employeeRepo, times(0)).deleteByEmail(anyString());
    }


    @Test
    void givenEmployeeToUpdateThenShouldReturnUpdatedEmployee(){

        when(employeeRepo.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(employeeRepo.save(employee)).thenReturn(employee);
        employee.setName("pavan");
        Boolean emp = employeeServiceImpl.updateProfile(employee);
        assertEquals(employee.getName(), "pavan");
        verify(employeeRepo, times(1)).save(employee);
        verify(employeeRepo, times(2)).findByEmail(employee.getEmail());
    }

    @Test
    void givenEmployeeToUpdateThenShould_Not_ReturnUpdatedEmployee(){

        when(employeeRepo.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->employeeServiceImpl.updateProfile(employee));

    }






}
