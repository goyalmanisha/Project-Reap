package com.projectreap.ProjectReap.service;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Role;
import com.projectreap.ProjectReap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User update(User user) {
        Iterator<User> iterator = userRepository.findAll().iterator();
        if (iterator.hasNext()) {
            user.setRole(Role.USER);
            userRepository.save(user);
        }
        else {
            userRepository.save(user);
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.getUsername(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.getEmail(email);
    }

    public Optional<User> findUserByResetToken(String resetToken){
        return userRepository.findByResetToken(resetToken);
    }

}
