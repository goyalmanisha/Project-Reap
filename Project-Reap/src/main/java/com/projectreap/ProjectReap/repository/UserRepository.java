package com.projectreap.ProjectReap.repository;

import com.projectreap.ProjectReap.entity.User;
import com.projectreap.ProjectReap.enums.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>{

    @Query("SELECT u FROM User u WHERE userName=:userName")
    User getUsername(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE email=:email ")
    Optional<User> getEmail(@Param("email") String email);

    /*
    * Fetching the list of users to be shown on admin page.
    * */
    List<User> getUsersByRole(String role);

    List<User> findByFirstNameLike(String firstname);

    List<User> findAll();

    User getUserById(Integer id);



//    @Query("SELECT u.badge from User u where ")
//    User getGoldBadge();

}
