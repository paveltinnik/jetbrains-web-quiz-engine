package com.paveltinnik.webquizengine.engine.repository;

import com.paveltinnik.webquizengine.engine.entities.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserRepositoryImpl {
    @Autowired
    MyUserRepository myUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void addUser(MyUser myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUserRepository.save(myUser);
    }

    public MyUser getUser(String email) {
        Optional<MyUser> user = myUserRepository.findMyUserByEmail(email);
        return user.orElse(null);
    }
}
