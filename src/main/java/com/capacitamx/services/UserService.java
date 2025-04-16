package com.capacitamx.services;

import com.capacitamx.models.Role;
import com.capacitamx.models.User;
import com.capacitamx.repositories.RoleRepository;
import com.capacitamx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
        //verifica y asigna roles
        Set<Role> existingRoles = user.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName()+" el id de este role es= "+ role.getId())
                        .orElseThrow( () -> new RuntimeException("Rol no encontrado: "+role.getName()))
                ).collect(Collectors.toSet());
        user.setRoles(existingRoles);
        return userRepository.save(user);
    }

    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User userDetails) {
        return userRepository.findById(id).map(
                user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());
                    user.setEmail(userDetails.getEmail());
                    user.setRoles(userDetails.getRoles());
                    return  userRepository.save(user);
        })
                .orElseThrow(
                        () -> new RuntimeException("Usuario no encontrado con id: "+id)
                );
    }

    public User updateUserRoles(String userId, List<String> roleNames){
        return userRepository.findById(userId).map(user -> {
            Set<Role> roles = roleNames.stream().map(
                    roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(
                                    () -> new RuntimeException("Rol no encontrado: "+roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
            return userRepository.save(user);
        }).orElseThrow(
                () -> new RuntimeException("Usuario con ID: "+userId+" no encontrado")
        );
    }

    public User removeUserRole(String userId, String roleName){
        return userRepository.findById(userId).map(user -> {
            Set<Role> updateRoles = user.getRoles().stream()
                    .filter(role -> role.getName().equals(roleName)) /// filtra y elimina el rol especiificado
                    .collect(Collectors.toSet());
            user.setRoles(updateRoles);
            return userRepository.save(user);
        }).orElseThrow(
                () -> new RuntimeException("No se encontró al usuario con ID: "+userId));
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }


}
