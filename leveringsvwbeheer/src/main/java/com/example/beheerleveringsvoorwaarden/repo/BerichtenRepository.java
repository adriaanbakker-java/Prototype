package com.example.beheerleveringsvoorwaarden.repo;


import com.example.beheerleveringsvoorwaarden.model.Bericht;
import com.example.beheerleveringsvoorwaarden.model.Berichtgegeven;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BerichtenRepository extends CrudRepository<Bericht, Long> {
    @Query(value =
            "select id, berichtnaam " +
                    "from bericht order by berichtnaam",
            nativeQuery = true)
    List<Bericht> getListBerichtenGesorteerd();

    Object findAll(Sort berichtnaam);
    Long findIdByBerichtnaam(String berichtnaam);

}
