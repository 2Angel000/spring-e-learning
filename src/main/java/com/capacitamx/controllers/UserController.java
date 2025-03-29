package com.capacitamx.controllers;

import com.capacitamx.models.User;
import com.capacitamx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}") // http://localhost:8080/api/users/profesor1 -> ver por contacto
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String id){
        Optional<User> user = userService.getUserById(id);
        return user.isPresent() ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails){
        User updateUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updateUser);
    }

    // Asignar roles a un usuario
    //URL: http://localhost:8080/users/{id}/roles
    //["ADMIN", "PROFESOR"]
    @PutMapping("/{id}/roles")
    public ResponseEntity<User> updateUserRoles(@PathVariable String id, @RequestBody List<String> roleNames){
        return ResponseEntity.ok(userService.updateUserRoles(id, roleNames));
    }

    @DeleteMapping("/{id}/roles/{roleName}")
    public ResponseEntity<User> removeUserRole(@PathVariable String id, @PathVariable String roleName){
        return ResponseEntity.ok(userService.removeUserRole(id, roleName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}