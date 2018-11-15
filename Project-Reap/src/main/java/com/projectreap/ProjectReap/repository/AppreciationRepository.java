package com.projectreap.ProjectReap.repository;

import com.projectreap.ProjectReap.entity.Appreciation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppreciationRepository extends CrudRepository<Appreciation,Integer> {

//        Appreciation getById();
}
