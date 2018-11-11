package com.projectreap.ProjectReap.service;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.entity.UserBadge;
import com.projectreap.ProjectReap.enums.Role;
import com.projectreap.ProjectReap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {

    public static final String[] ROLE_USER =new String[]{"USER","user"};

    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;




    public User update(User user) {
        Iterator<User> iterator = userRepository.findAll().iterator();
        if (iterator.hasNext()) {
            user.setRole(Role.USER.getValue());

            UserBadge userBadge=new UserBadge(3,2,1);

            user.setBadge(userBadge);
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

     public List<User> getAllUsers(){
       return userRepository.getUsersByRole(Role.USER.getValue());
    }

    @Transactional
    public Integer  getPasswordById(int id){
        return userRepository.getById(id);
    }

    public List<User> getUsersFirstName(String firstname){
        return userRepository.findByFirstName(firstname);
    }

    /*
    * Method to fetch all the users
    * */
    public List<User> getAllUserList(){
        return userRepository.getUsers();
    }
}
