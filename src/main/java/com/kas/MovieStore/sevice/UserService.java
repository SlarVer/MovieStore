package com.kas.MovieStore.sevice;

import com.kas.MovieStore.entity.Role;
import com.kas.MovieStore.entity.User;
import com.kas.MovieStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User \"" + username + "\" not found");
        }
        return user;
    }

    public boolean saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null){
            return false;
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            user.setProfilePic("0.png");
            userRepository.save(user);
            return true;
        }
    }

    public boolean checkPassword(String username, String rawPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, userRepository.findByUsername(username).getPassword());
    }

    @Transactional
    public void updatePassword(String username, String password) {
        userRepository.findByUsername(username).setPassword(bCryptPasswordEncoder.encode(password));
    }
}
