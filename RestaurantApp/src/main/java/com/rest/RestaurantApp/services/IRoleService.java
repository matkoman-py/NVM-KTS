package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.Role;

import java.util.List;

public interface IRoleService {

    Role findById(int id);

    List<Role> findByName(String name);

}
