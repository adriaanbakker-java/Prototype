package com.example.beheerleveringsvoorwaarden.repo;


import com.example.beheerleveringsvoorwaarden.model.Leveringsvoorwaarde;
import com.example.beheerleveringsvoorwaarden.model.Voorwaarde;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeveringsvoorwaardenRepository extends CrudRepository<Leveringsvoorwaarde, Long> {

    Object findAll(Sort berichtnaam);

}
