package com.example.beheerleveringsvoorwaarden.service;

import java.util.List;
import java.util.Optional;

import com.example.beheerleveringsvoorwaarden.model.VoorwaardeDto;
import com.example.beheerleveringsvoorwaarden.repo.Voorwaarde;
import com.example.beheerleveringsvoorwaarden.repo.VoorwaardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class VoorwaardenbeheerService {
    @Autowired
    VoorwaardenRepository repository;

    public void add(VoorwaardeDto dto) {
        System.out.println("toevoegen van voorwaarde:" + dto.getBerichtnaam());
        Voorwaarde vw = toEntity(dto);
        repository.save(vw);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<Voorwaarde> getLijstVoorwaarden() {
        return (List<Voorwaarde>) repository.findAll(Sort.by(Sort.Direction.ASC, "berichtnaam"));
    }




    public Voorwaarde getVoorwaardeById(long id) {
        Optional<Voorwaarde> optionalVoorwaarde = repository.findById(id);
        return optionalVoorwaarde.orElseThrow(() -> new voorwaardeNietGevondenExceptie("Kon voorwaarde met id niet vinden: " + id));
    }

    private Voorwaarde toEntity(VoorwaardeDto dto) {
        Voorwaarde entity = new Voorwaarde();
        entity.setBerichtnaam(dto.getBerichtnaam());
        entity.setLeveringsdoel(dto.getLeveringsdoel());
        entity.setPadnaargegeven(dto.getPadnaargegeven());
        return entity;
    }



    public Object getLijstVoorwaardenGesorteerd() {
        return (List<Voorwaarde>) repository.getListVoorwaardenlijstGesorteerd();
    }
}
