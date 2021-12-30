package com.rest.RestaurantApp.repositories;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriviligedUserRepository extends AbstractUserRepository<PrivilegedUser>{

    @Query("select pu from PrivilegedUser pu join fetch pu.roles pur where pu.username = :username")
    Optional<PrivilegedUser> findByUsername(String username);
}
