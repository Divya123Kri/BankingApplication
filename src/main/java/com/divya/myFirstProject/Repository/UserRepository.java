package com.divya.myFirstProject.Repository;

import com.divya.myFirstProject.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByUsername(String username);
}
