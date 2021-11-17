package com.rest.RestaurantApp.repositories;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public interface PriviligedUserRepository extends AbstractUserRepository<PrivilegedUser>{


    @Query("select pu from PrivilegedUser pu where pu.username = :username")
    PrivilegedUser findByUsername(String username);
}
