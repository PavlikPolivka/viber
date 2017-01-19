package com.ppolivka.viber.repository;

import com.ppolivka.viber.model.Robot;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "robots", collectionResourceRel = "robot", itemResourceRel = "robot")
public interface RobotRepository extends PagingAndSortingRepository<Robot, Integer> {

    @RestResource(path = "byName")
    List<Robot> findByLastName(@Param("name") String name);
}
