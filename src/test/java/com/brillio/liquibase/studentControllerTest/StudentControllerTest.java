package com.brillio.liquibase.studentControllerTest;

import com.brillio.liquibase.controller.studentController.StudentController;
import com.brillio.liquibase.model.student.Student;
import com.brillio.liquibase.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers= StudentController.class)
public class StudentControllerTest {

    @Autowired
    StudentController studentController;

    @MockBean
    StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private Student student;

    public static final String addProfileURL = "/api/v1/student/addProfile";
    public static final String allProfileURL = "/api/v1/student/getAllProfile";
    public static final String updateStudentURL = "/api/v1/student/updateProfile";
    public static final String deleteStudent = "/api/v1/student/deleteProfile";


    @BeforeEach
    void setUp(){

        student=new Student(1,"arjun","male","email@gmail.com",17,"hyderabad",70);
    }


    @Test
    void addProfileTest() throws Exception{
        when(studentService.addProfile(student)).thenReturn(student);
        mockMvc.perform(post(addProfileURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllProfileTest() throws Exception{
        when(studentService.allProfiles()).thenReturn(Arrays.asList(student));
        mockMvc.perform(get(allProfileURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isOk());
        verify(studentService,times(1)).allProfiles();
    }

    @Test
    void studentDeletedTest() throws Exception{
        when(studentService.deleteProfile("student@gmail.com")).thenReturn(null);
        mockMvc.perform(delete(deleteStudent+"/student@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateStudentTest() throws Exception{
        when(studentService.updateProfile(student)).thenReturn(true);
        mockMvc.perform(put(updateStudentURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateStudentTestThrowException() throws Exception{
        when(studentService.updateProfile(student)).thenReturn(false);
        mockMvc.perform(put(updateStudentURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student)))
                .andExpect(status().isNotFound());
    }


}
