package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.SalaryInfo;

public interface SalaryInfoRepository extends JpaRepository<SalaryInfo, Integer>{

}
