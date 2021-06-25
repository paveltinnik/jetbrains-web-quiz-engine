package com.paveltinnik.webquizengine.engine.repository;

import com.paveltinnik.webquizengine.engine.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findMyUserByEmail(String email);
}