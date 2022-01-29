package com.rest.RestaurantApp.repositories;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.EmployeeLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeLoginInfoRepository extends JpaRepository<EmployeeLoginInfo, Integer> {

    @Query("select eli from EmployeeLoginInfo eli where eli.employeeType = :type")
    EmployeeLoginInfo findByType(String type);
}
