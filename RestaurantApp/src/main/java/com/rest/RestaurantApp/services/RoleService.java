package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.Role;
import com.rest.RestaurantApp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(int id) {
        Role role = this.roleRepository.getOne(id);
        return role;
    }

    @Override
    public List<Role> findByName(String name) {
        List<Role> roles = this.roleRepository.findByName(name);
        return roles;
    }
}
