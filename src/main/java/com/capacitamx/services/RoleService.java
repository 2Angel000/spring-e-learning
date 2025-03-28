package com.capacitamx.services;

import com.capacitamx.models.Role;
import com.capacitamx.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role createRole(Role role){
        return roleRepository.save(role);
    }

    public Role updateRole(String id, Role roleDetails){
        return roleRepository.findById(id).map(
                role -> {
                    role.setName(roleDetails.getName());
                    return roleRepository.save(role);
                }
        ).orElseThrow(
                () -> new RuntimeException("Rol con id: "+id+" No encontrado")
        );
    }

    public void deleteRole(String id){
        if(!roleRepository.existsById(id)){
            throw new RuntimeException("Rol con ID: "+id+" No encontrado");
        }
        roleRepository.deleteById(id);
    }

}
