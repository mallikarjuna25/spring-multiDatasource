package com.brillio.liquibase.model.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_table")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private int phone;

    @Column(name = "title")
    private String title;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "bonus")
    private Integer bonus;
}

