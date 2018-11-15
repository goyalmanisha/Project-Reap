package com.projectreap.ProjectReap.repository;

import com.projectreap.ProjectReap.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {


    User findByUserName(String userName);


    Optional<User> findByEmail(String email);

    /*
     * Fetching the list of users to be shown on admin page.
     * */
    List<User> getUsersByRole(String role);

    List<User> findByFirstNameLike(String firstname);

    List<User> findAll();

    User getUserById(Integer id);

}
