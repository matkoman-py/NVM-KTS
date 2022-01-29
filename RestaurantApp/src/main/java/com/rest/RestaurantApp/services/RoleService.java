package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.Role;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(int id) {
        Optional<Role> role = this.roleRepository.findById(id);
        
        if(role.isEmpty()) {
        	throw new NotFoundException("Role with id: " + id + " not found");
        }
        return role.get();
    }

    @Override
    public List<Role> findByName(String name) {
        List<Role> roles = this.roleRepository.findByName(name);
        return roles;
    }
}
