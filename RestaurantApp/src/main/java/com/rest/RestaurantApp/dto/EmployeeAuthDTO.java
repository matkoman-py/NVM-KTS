package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.enums.EmployeeType;

public class EmployeeAuthDTO {

    private int id;
    private EmployeeType employeeType;
    private String email;

    public EmployeeAuthDTO(int id, EmployeeType employeeType, String email) {
        this.id = id;
        this.employeeType = employeeType;
        this.email = email;
    }

    public EmployeeAuthDTO(Employee employee) {
        this.id = employee.getId();
        this.employeeType = employee.getEmployeeType();
        this.email = employee.getEmail();
    }

    public EmployeeAuthDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
