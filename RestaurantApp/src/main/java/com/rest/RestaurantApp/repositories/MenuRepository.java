package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rest.RestaurantApp.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{

}
