package com.rest.RestaurantApp.repositories;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import org.springframework.data.jpa.repository.JpaRepository;


import com.rest.RestaurantApp.domain.PriceInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Integer>{
}
