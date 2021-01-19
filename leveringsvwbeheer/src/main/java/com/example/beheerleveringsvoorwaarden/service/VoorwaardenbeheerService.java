package com.example.beheerleveringsvoorwaarden.service;

import java.util.List;
import java.util.Optional;

import com.example.beheerleveringsvoorwaarden.model.*;
import com.example.beheerleveringsvoorwaarden.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class VoorwaardenbeheerService {
    @Autowired
    VoorwaardenRepository voorwaardenRepository;

    @Autowired
    LeveringsvoorwaardenRepository leveringsvoorwaardenRepository;

    @Autowired
    BerichtenRepository berichtenRepository;

    @Autowired
    LeveringsdoelRepository leveringsdoelRepository;

    public void add(VoorwaardeDto dto) {
        System.out.println("toevoegen van voorwaarde:" + dto.getBerichtnaam());
        Voorwaarde vw = toEntity(dto);
        voorwaardenRepository.save(vw);
    }

    public void delete(long id) {
        voorwaardenRepository.deleteById(id);
    }

    public List<Voorwaarde> getLijstVoorwaarden() {
        return (List<Voorwaarde>) voorwaardenRepository.findAll(Sort.by(Sort.Direction.ASC, "berichtnaam"));
    }




    public Voorwaarde getVoorwaardeById(long id) {
        Optional<Voorwaarde> optionalVoorwaarde = voorwaardenRepository.findById(id);
        return optionalVoorwaarde.orElseThrow(() -> new voorwaardeNietGevondenExceptie("Kon voorwaarde met id niet vinden: " + id));
    }

    private Voorwaarde toEntity(VoorwaardeDto dto) {
        Voorwaarde entity = new Voorwaarde();
        entity.setBerichtnaam(dto.getBerichtnaam());
        entity.setLeveringsdoel(dto.getLeveringsdoel());
        entity.setPadnaargegeven(dto.getPadnaargegeven());
        return entity;
    }

    public List<Voorwaarde> getLijstVoorwaarden(String berichtnaam, String leveringsdoel) {
        return  voorwaardenRepository.getVoorwaarden(berichtnaam, leveringsdoel);
    }


    public List<Bericht> getLijstBerichten() {
        List<Bericht> result = (List<Bericht>)  berichtenRepository.getListBerichtenGesorteerd();
        return result;
    }

    public List<Leveringsdoel> getLijstLeveringsdoelen() {
        List<Leveringsdoel> result =  leveringsdoelRepository.getListLeveringsdoelGesorteerd();
        return result;
    }

    public void deleteVoorwaarde(long id) {
        voorwaardenRepository.deleteById(id);
    }



    public List<Voorwaarde> getLijstLeveringsvoorwaarden(String berichtnaam, String leveringsdoel) {
        return leveringsvoorwaardenRepository.getLeveringsvoorwaarden(berichtnaam, leveringsdoel);
    }
}
