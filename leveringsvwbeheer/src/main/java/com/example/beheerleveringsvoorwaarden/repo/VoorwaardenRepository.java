package com.example.beheerleveringsvoorwaarden.repo;


import com.example.beheerleveringsvoorwaarden.model.Voorwaarde;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoorwaardenRepository extends CrudRepository<Voorwaarde, Long> {



    Object findAll(Sort berichtnaam);

    @Query(value =
            "select id, berichtnaam, leveringsdoel, padnaargegeven" +
                    " from voorwaarde "+
                    " where ((berichtnaam = ?1 ) or (?1 = ''))"  +
                    " and   ((leveringsdoel = ?2) or (?2 = ''))"  +
                    " order by berichtnaam, leveringsdoel, padnaargegeven",
            nativeQuery = true)
    List<Voorwaarde> getVoorwaarden(String berichtnaam, String leveringsdoel);


}
