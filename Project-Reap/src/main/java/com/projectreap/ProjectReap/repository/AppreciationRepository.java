package com.projectreap.ProjectReap.repository;

import com.projectreap.ProjectReap.entity.Appreciation;
import com.projectreap.ProjectReap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppreciationRepository extends JpaRepository<Appreciation,Integer> {

//    @Query("SELECT ")
//    Appreciation getBadge();

//    @Query("SELECT count(appreciatedUser), appreciatedUser FROM Appreciation GROUP BY appreciatedUser")
//    Appreciation groupByAppreciatedUser();

    List<Appreciation> findAllByAppreciatedUser(User user);

    String findAllByBadge(List<Appreciation> appreciation);

}
