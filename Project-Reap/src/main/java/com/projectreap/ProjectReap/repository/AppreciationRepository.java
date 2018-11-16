package com.projectreap.ProjectReap.repository;

import com.projectreap.ProjectReap.entity.Appreciation;
import com.projectreap.ProjectReap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppreciationRepository extends JpaRepository<Appreciation, Integer> {

    List<Appreciation> findAllByAppreciatedUser(User user);

}
