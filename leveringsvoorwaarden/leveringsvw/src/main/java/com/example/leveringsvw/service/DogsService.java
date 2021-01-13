package com.example.leveringsvw.service;

import java.util.List;
import java.util.Optional;

import com.example.leveringsvw.model.VoorwaardeDto;
import com.example.leveringsvw.repo.Voorwaarde;
import com.example.leveringsvw.repo.DogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DogsService {
    @Autowired DogsRepository repository;

    public void add(VoorwaardeDto dto) {
        System.out.println("toevoegen van voorwaarde:" + dto.getBerichtnaam());
        repository.save(toEntity(dto));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<Voorwaarde> getDogs() {
        return (List<Voorwaarde>) repository.findAll(Sort.by(Sort.Direction.ASC, "berichtnaam"));
    }




    public Voorwaarde getDogById(long id) {
        Optional<Voorwaarde> optionalDog = repository.findById(id);
        return optionalDog.orElseThrow(() -> new DogNotFoundException("Kon voorwaarde met id niet vinden: " + id));
    }

    private Voorwaarde toEntity(VoorwaardeDto dto) {
        Voorwaarde entity = new Voorwaarde();
        entity.setBerichtnaam(dto.getBerichtnaam());
        entity.setAge(dto.getAge());
        return entity;
    }



    public Object getOldDogs() {
        return (List<Voorwaarde>) repository.findOldDogs();
    }
}
