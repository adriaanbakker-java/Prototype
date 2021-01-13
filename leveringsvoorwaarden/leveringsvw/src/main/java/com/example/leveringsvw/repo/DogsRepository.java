package com.example.leveringsvw.repo;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogsRepository extends CrudRepository<Voorwaarde, Long> {

    @Query(value = "select id, berichtnaam, age from voorwaarde where age > 10 order by berichtnaam", nativeQuery = true)
    List<Voorwaarde> findOldDogs();

    Object findAll(Sort berichtnaam);
}
