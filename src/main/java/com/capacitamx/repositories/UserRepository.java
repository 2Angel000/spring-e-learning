package com.capacitamx.repositories;

import com.capacitamx.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public  interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
