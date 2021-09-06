package com.brillio.liquibase.employeeControllerTest;

import com.brillio.liquibase.controller.employeeController.EmployeeController;
import com.brillio.liquibase.model.employee.Employee;
import com.brillio.liquibase.model.student.Student;
import com.brillio.liquibase.service.EmployeeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers=EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    EmployeeController employeeController;

    @MockBean
    EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private Employee employee;

    public static final String addProfileURL = "/api/v1/employee/addProfile";
    public static final String allProfileURL = "/api/v1/employee/getAllProfile";
    public static final String updateEmployeeURL = "/api/v1/employee/updateProfile";
    public static final String deleteEmployee = "/api/v1/employee/deleteProfile";


    @BeforeEach
    void setUp(){

        employee=new Employee(1,"malli","male",12345555,"developer","malli@gmail.com",50000);
    }


    @Test
    void addProfileTest() throws Exception{
        when(employeeService.addProfile(employee)).thenReturn(employee);
        mockMvc.perform(post(addProfileURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllProfileTest() throws Exception{
        when(employeeService.allProfiles()).thenReturn(Arrays.asList(employee));
        mockMvc.perform(get(allProfileURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andExpect(status().isOk());
        verify(employeeService,times(1)).allProfiles();
    }

    @Test
    void employeeDeletedTest() throws Exception{
        when(employeeService.deleteProfile("malli@gmail.com")).thenReturn(null);
        mockMvc.perform(delete(deleteEmployee+"/malli@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateEmployeeTest() throws Exception{
        when(employeeService.updateProfile(employee)).thenReturn(true);
        mockMvc.perform(put(updateEmployeeURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateEmployeeTestThrowException() throws Exception{
        when(employeeService.updateProfile(employee)).thenReturn(false);
        mockMvc.perform(put(updateEmployeeURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound());
    }

}
