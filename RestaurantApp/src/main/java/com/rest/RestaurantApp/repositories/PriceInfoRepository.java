package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.rest.RestaurantApp.domain.PriceInfo;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Integer>{

}
