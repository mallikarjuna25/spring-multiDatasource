package com.brillio.liquibase.controller.employeeController;

import com.brillio.liquibase.exception.UserNotFoundException;
import com.brillio.liquibase.model.employee.Employee;
import com.brillio.liquibase.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {


    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping("addProfile")
    public ResponseEntity<?> addProfile(@RequestBody Employee employee){
        return new ResponseEntity<>(service.addProfile(employee),HttpStatus.CREATED);
    }

    @GetMapping("getAllProfile")
    public ResponseEntity<?> getAllProfiles(){
        return new ResponseEntity<>(service.allProfiles(), HttpStatus.OK);
    }

    @PutMapping("updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody Employee employee){
        Boolean check = service.updateProfile(employee);
        if(check)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else{
            throw new UserNotFoundException("no data found");
        }
    }

    @DeleteMapping("deleteProfile/{email}")
    public ResponseEntity<?> deleteProfile(@PathVariable String email){
        return new ResponseEntity<>(service.deleteProfile(email),HttpStatus.NO_CONTENT);
    }

}

