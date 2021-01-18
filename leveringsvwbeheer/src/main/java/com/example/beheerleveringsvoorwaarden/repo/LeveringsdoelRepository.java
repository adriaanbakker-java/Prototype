package com.example.beheerleveringsvoorwaarden.repo;

import com.example.beheerleveringsvoorwaarden.model.Leveringsdoel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeveringsdoelRepository extends CrudRepository<Leveringsdoel, Long> {
    @Query(value =
            "select id, leveringsdoel " +
                    "from leveringsdoel order by leveringsdoel",
            nativeQuery = true)
    List<Leveringsdoel> getListLeveringsdoelGesorteerd();

    Object findAll(Sort leveringsdoel);
}