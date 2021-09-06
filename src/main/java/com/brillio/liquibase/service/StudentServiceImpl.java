package com.brillio.liquibase.service;

import com.brillio.liquibase.exception.UserExistsException;
import com.brillio.liquibase.exception.UserNotFoundException;
import com.brillio.liquibase.exception.UserNullPointerException;
import com.brillio.liquibase.model.student.Student;
import com.brillio.liquibase.repository.studentRepo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepo repo;

    @Autowired
    public StudentServiceImpl(StudentRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Student> allProfiles() {
        return repo.findAll();
    }

    @Override
    public Student addProfile(Student student) throws UserExistsException {
        if(repo.existsByEmail(student.getEmail())){
            throw new UserExistsException("Profile Already exists With Email");
        }
        else if(student.getEmail()== null || student.getEmail().isEmpty()) {
            throw new UserNullPointerException("email is required to create a profile");
        }
        Student savedProfile = repo.save(student);
        return savedProfile;
    }

    @Override
    public Boolean updateProfile(Student student) throws UserNotFoundException {
        Optional<Student> byEmail = repo.findByEmail(student.getEmail());
        if(byEmail.isPresent()){
            Student stud = repo.findByEmail(student.getEmail()).get();
            stud.setName(student.getName());
            stud.setEmail(student.getEmail());
            stud.setGender(student.getGender());
            stud.setAge(student.getAge());
            stud.setScore(student.getScore());
            stud.setAddress(student.getAddress());
            repo.save(stud);

            return true;
        }
        else{
            throw new UserNotFoundException("Profile Not Found");
        }
    }

    @Override
    public Student deleteProfile(String email) throws UserNotFoundException {
        if(repo.existsByEmail(email)){
            Student name = repo.deleteByEmail(email);
            return name;
        }
        throw new UserNotFoundException("Profile Not Found");
    }
}
