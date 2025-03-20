package com.capacitamx.services;

import com.capacitamx.models.Role;
import com.capacitamx.models.User;
import com.capacitamx.repositories.RoleRepository;
import com.capacitamx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(  String username, String email, String password, String roleName){
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("Correo existente");
        }

        //Encriptar contraseña
        String encryptedPassword = passwordEncoder.encode(password);

        //Buscar el rol en mongoDB
        Optional<Role> roleOptional = roleRepository.findByName(roleName);
        Role role = roleOptional.orElseThrow(()->new RuntimeException("Rol no encontrado"));

        //Asignar rol a usuario
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        //crear usuario
        User newUser = new User(username, email, encryptedPassword, roles);
        return userRepository.save(newUser);
    }
}
