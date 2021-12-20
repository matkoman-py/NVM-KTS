package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAuthDTO {

    private int id;
    private EmployeeType employeeType;
    private String email;

    public EmployeeAuthDTO(Employee employee) {
        this.id = employee.getId();
        this.employeeType = employee.getEmployeeType();
        this.email = employee.getEmail();
    }
}
