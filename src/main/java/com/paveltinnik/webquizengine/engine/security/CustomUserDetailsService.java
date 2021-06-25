package com.paveltinnik.webquizengine.engine.security;

import com.paveltinnik.webquizengine.engine.entities.MyUser;
import com.paveltinnik.webquizengine.engine.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUser = myUserRepository.findMyUserByEmail(username);

        if (myUser.isEmpty()) {
            throw new UsernameNotFoundException("Unknown user " + username);
        }

        UserDetails user = User.builder()
                .username(myUser.get().getEmail())
                .password(myUser.get().getPassword())
                .roles("ADMIN")
                .build();

        return user;
    }
}
