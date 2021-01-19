package com.example.beheerleveringsvoorwaarden.repo;


import com.example.beheerleveringsvoorwaarden.model.Bericht;
import com.example.beheerleveringsvoorwaarden.model.Berichtgegeven;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BerichtgegevenRepository extends CrudRepository<Berichtgegeven, Long> {



    @Query(value =
            "select berichtgegeven.id, berichtgegeven.bericht_id, berichtgegeven.padnaargegeven" +
                    " from berichtgegeven, bericht" +
                    " where bericht.id = berichtgegeven.bericht_id and" +
                    " bericht.berichtnaam = ?1 and" +
                    " berichtgegeven.padnaargegeven = ?2",
            nativeQuery = true)
    Berichtgegeven findByBerichtnaamAndPad(String berichtnaam, String padnaargegeven);
}
