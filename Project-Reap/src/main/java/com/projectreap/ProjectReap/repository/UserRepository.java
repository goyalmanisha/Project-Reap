package com.projectreap.ProjectReap.repository;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE userName=:userName")
    User getUsername(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE email=:email ")
    Optional<User> getEmail(@Param("email") String email);

    /*
    * Fetching the list of users to be shown on admin page.
    * */
    List<User> getUsersByRole(Role role);

    /*
    * Query to set the password manually at forget password functionality.
    * */
    @Modifying
    @Query("UPDATE User u SET u.password='abcd' where u.id=:id")
    Integer getById(@Param("id") int id);

    List<User> findByFirstName(String firstname);

    @Query("Select u from User u")
    List<User> getUsers();
}
