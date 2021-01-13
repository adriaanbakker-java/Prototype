package com.example.leveringsvw.repo;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoorwaardenRepository extends CrudRepository<Voorwaarde, Long> {

    @Query(value = "select id, berichtnaam, leveringsdoel from voorwaarde order by berichtnaam, leveringsdoel", nativeQuery = true)
    List<Voorwaarde> getListVoorwaardenlijstGesorteerd();

    Object findAll(Sort berichtnaam);
}
