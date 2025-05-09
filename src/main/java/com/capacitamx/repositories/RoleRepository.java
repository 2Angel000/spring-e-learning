package com.capacitamx.repositories;

import com.capacitamx.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
    Optional<Role> findById(String id);
}
