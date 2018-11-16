package com.projectreap.ProjectReap.service;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.entity.UserBadge;
import com.projectreap.ProjectReap.enums.Role;
import com.projectreap.ProjectReap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //public static final String[] ROLE_USER = new String[]{"USER", "user"};

    @Autowired
    UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User save(User user){
        return userRepository.save(user);
    }

    public User update(User user) {
        Iterator<User> iterator = userRepository.findAll().iterator();
        if (!iterator.hasNext()) {
            provideDefaultBadgeByRole(user);
            userRepository.save(user);
        } else {
            provideDefaultBadgeByRole(user);
            userRepository.save(user);
        }
        return user;
    }


    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsersByRole(String role) {
        return userRepository.getUsersByRole(role);
    }


    public List<User> getUsersFirstName(String firstname) {
        return userRepository.findByFirstNameLike("%" + firstname + "%");
    }

    /*
     * Method to fetch all the users
     * */
    public List<User> getAllUsersList() {
        return userRepository.findAll();
    }

    public User updateUserDetails(String role, Boolean status, Integer id) {
        User user = userRepository.getUserById(id);
        user.setRole(role);
        user.setStatus(status);
        provideDefaultBadgeByRole(user);
        return userRepository.save(user);

    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public User getById(Integer id){
       return userRepository.getUserById(id);
    }
    /*
     * Setting Default Badge on the basis of role
     * */
    public User provideDefaultBadgeByRole(User user) {
        if (user.getRole().equals(Role.USER.getValue())) {
            user.setBadge(new UserBadge(3, 2, 1));
        } else if (user.getRole().equals(Role.SUPERVISOR.getValue())) {
            user.setBadge(new UserBadge(6, 3, 2));
        } else if (user.getRole().equals(Role.PRACTICE_HEAD.getValue())) {
            user.setBadge(new UserBadge(9, 6, 3));
        } else {
            //user.setRole(Role.ADMIN.getValue());
            user.setBadge(new UserBadge(0, 0, 0));
        }
        return user;
    }
}