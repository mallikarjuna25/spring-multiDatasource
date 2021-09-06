package com.brillio.liquibase.studentServiceTest;

import com.brillio.liquibase.exception.UserNotFoundException;
import com.brillio.liquibase.model.employee.Employee;
import com.brillio.liquibase.model.student.Student;
import com.brillio.liquibase.repository.employeeRepo.EmployeeRepo;
import com.brillio.liquibase.repository.studentRepo.StudentRepo;
import com.brillio.liquibase.service.EmployeeServiceImpl;
import com.brillio.liquibase.service.StudentServiceImpl;
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
public class StudentServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;
    private Student student;
    private List<Student> studentList;

    @BeforeEach
    void setUp(){
        student=new Student(1,"malli","male","malli@gmail.com",18,"bangalore",80);
    }

    @AfterEach
    void tearDown(){
        student=null;
    }

    @Test
    void givenStudentToSaveThenShouldReturnSavedStudent() {
        when(studentRepo.save(any())).thenReturn(student);
        assertEquals(student,studentServiceImpl.addProfile(student));
    }

    @Test
    void givenStudentToSaveThenShouldNotReturnSavedStudent(){
        when(studentRepo.save(any())).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class,()->studentServiceImpl.addProfile(student));
    }

    @Test
    void givenGetAllStudentsThenShouldReturnListOfAllStudents(){
        Student studentNew = new Student(2,"arjun","male","arjun@gmail.com",19,"karnataka",60);
        studentServiceImpl.addProfile(student);
        studentServiceImpl.addProfile(studentNew);

        when(studentRepo.findAll()).thenReturn(studentList);
        List<Student> studentListGet = (List<Student>) studentServiceImpl.allProfiles();
        assertEquals(studentList,studentListGet);
        verify(studentRepo, times(1)).findAll();
    }

    @Test
    void givenStudentEmailToDeleteThenShouldReturnDeletedStudent(){
        when(studentRepo.existsByEmail(student.getEmail())).thenReturn(true);
        when(studentRepo.deleteByEmail(student.getEmail())).thenReturn(student);
        assertEquals(student,studentServiceImpl.deleteProfile(student.getEmail()));
        verify(studentRepo, times(1)).existsByEmail(anyString());
        verify(studentRepo, times(1)).deleteByEmail(anyString());

    }

    @Test
    void givenStudentEmailToDeleteThenShould_Not_ReturnDeletedStudent(){
        when(studentRepo.existsByEmail(student.getEmail())).thenReturn(false);
        assertThrows(UserNotFoundException.class,()->studentServiceImpl.deleteProfile(student.getEmail()));
        verify(studentRepo, times(1)).existsByEmail(anyString());
        verify(studentRepo, times(0)).deleteByEmail(anyString());
    }


    @Test
    void givenStudentToUpdateThenShouldReturnUpdatedStudent(){

        when(studentRepo.findByEmail(student.getEmail())).thenReturn(Optional.of(student));
        when(studentRepo.save(student)).thenReturn(student);
        student.setName("pavan");
        Boolean emp = studentServiceImpl.updateProfile(student);
        assertEquals(student.getName(), "pavan");
        verify(studentRepo, times(1)).save(student);
        verify(studentRepo, times(2)).findByEmail(student.getEmail());
    }

    @Test
    void givenStudentToUpdateThenShould_Not_ReturnUpdatedStudent(){

        when(studentRepo.findByEmail(student.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->studentServiceImpl.updateProfile(student));

    }






}

