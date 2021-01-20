package com.example.beheerleveringsvoorwaarden.repo;


import com.example.beheerleveringsvoorwaarden.model.Leveringsvoorwaarde;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeveringsvoorwaardenRepository extends CrudRepository<Leveringsvoorwaarde, Long> {

    Object findAll(Sort berichtnaam);

}
