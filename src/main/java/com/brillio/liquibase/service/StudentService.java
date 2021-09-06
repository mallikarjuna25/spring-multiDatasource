package com.brillio.liquibase.service;

import com.brillio.liquibase.exception.UserExistsException;
import com.brillio.liquibase.exception.UserNotFoundException;
import com.brillio.liquibase.model.student.Student;

import java.util.List;

public interface StudentService {

    List<Student> allProfiles();
    Student addProfile(Student student) throws UserExistsException;
    Boolean updateProfile(Student student) throws UserNotFoundException;
    Student deleteProfile(String email) throws UserNotFoundException;

}
