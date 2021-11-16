package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.rest.RestaurantApp.domain.User;

@NoRepositoryBean
public interface AbstractUserRepository <T extends User> extends JpaRepository<T, Integer> {

}
