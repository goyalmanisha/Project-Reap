package com.projectreap.ProjectReap.repository;

import com.projectreap.ProjectReap.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE userName=:userName")
    User getUsername(@Param("userName") String userName);

    @Query("SELECT u FROM User u WHERE email=:email ")
    Optional<User> getEmail(@Param("email") String email);

    Optional<User> findByResetToken(String resetToken);


//    @Query("SELECT id,firstName,lastName,userName,email,role FROM User WHERE userName=:username AND password=:pwd ")
//    User getUserDetails(@Param("username") String userName,@Param("pwd") String password){
//        Map m=new HashMap();
//        m.put()
//    }

}
