package com.example.leveringsvw.service;

import java.util.List;
import java.util.Optional;

import com.example.leveringsvw.model.DogDto;
import com.example.leveringsvw.repo.Dog;
import com.example.leveringsvw.repo.DogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class DogsService {
    @Autowired DogsRepository repository;

    public void add(DogDto dto) {
        System.out.println("adding dog" + dto.getName());
        repository.save(toEntity(dto));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public List<Dog> getDogs() {
        return (List<Dog>) repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }




    public Dog getDogById(long id) {
        Optional<Dog> optionalDog = repository.findById(id);
        return optionalDog.orElseThrow(() -> new DogNotFoundException("Couldn't find a Dog with id: " + id));
    }

    private Dog toEntity(DogDto dto) {
        Dog entity = new Dog();
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        return entity;
    }



    public Object getOldDogs() {
        return (List<Dog>) repository.findOldDogs();
    }
}
