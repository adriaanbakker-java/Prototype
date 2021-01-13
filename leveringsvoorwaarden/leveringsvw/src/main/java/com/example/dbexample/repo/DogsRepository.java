package com.example.dbexample.repo;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogsRepository extends CrudRepository<Dog, Long> {

    @Query(value = "select id, name, age from dog where age > 10 order by name", nativeQuery = true)
    List<Dog> findOldDogs();

    Object findAll(Sort name);
}
