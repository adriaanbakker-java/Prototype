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
    VoorwaardenRepository voorwaardenRepository; // nodig tbv ophalen lijst

    @Autowired
    LeveringsvoorwaardenRepository leveringsvoorwaardenRepository;  // nodig tbv delete

    @Autowired
    BerichtenRepository berichtenRepository;

    @Autowired
    BerichtgegevenRepository berichtgegevenRepository;

    @Autowired
    LeveringsdoelRepository leveringsdoelRepository;




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



    public List<Voorwaarde> getLijstVoorwaarden(String berichtnaam, String leveringsdoel) {
        return  voorwaardenRepository.getLeveringsvoorwaarden(berichtnaam, leveringsdoel);
    }


    public List<Bericht> getLijstBerichten() {
        List<Bericht> result = (List<Bericht>)  berichtenRepository.getListBerichtenGesorteerd();
        return result;
    }

    public List<Leveringsdoel> getLijstLeveringsdoelen() {
        List<Leveringsdoel> result =  leveringsdoelRepository.getListLeveringsdoelGesorteerd();
        return result;
    }

    public void deleteLeveringsvoorwaarde(long id) {
        leveringsvoorwaardenRepository.deleteById(id);
    }

    public List<Voorwaarde> getLijstLeveringsvoorwaarden(String berichtnaam, String leveringsdoel) {
        return voorwaardenRepository.getLeveringsvoorwaarden(berichtnaam, leveringsdoel);
    }


    private Voorwaarde toVoorwaarde(VoorwaardeDto dto) {
        Voorwaarde entity = new Voorwaarde();
        entity.setBerichtnaam(dto.getBerichtnaam());
        entity.setLeveringsdoel(dto.getLeveringsdoel());
        entity.setPadnaargegeven(dto.getPadnaargegeven());
        return entity;
    }

    public void addVoorwaarde(VoorwaardeDto dto) {
        System.out.println("toevoegen van voorwaarde:" + dto.getBerichtnaam());
        Voorwaarde vw = toVoorwaarde(dto);
        voorwaardenRepository.save(vw);
    }




    public void addLeveringsvoorwaarde(LeveringsvoorwaardeDto dto) throws Exception {
        Leveringsvoorwaarde lvw = new Leveringsvoorwaarde();
        Optional<Berichtgegeven> berichtgegeven = berichtgegevenRepository.findById(dto.getBerichtgegevenid());
        if (!berichtgegeven.isPresent()) {
            throw new Exception("addLeveringsvoorwaarde: berichtgegeven niet gevonden");
        }
        lvw.setBerichtgegeven(berichtgegeven.get());

        Optional<Leveringsdoel> leveringsdoel = leveringsdoelRepository.findById(dto.getLeveringsdoelid());
        if (!leveringsdoel.isPresent()) {
            throw new Exception("addLeveringsvoorwaarde: leveringsdoel niet gevonden");
        }
        lvw.setLeveringsdoel(leveringsdoel.get());
        leveringsvoorwaardenRepository.save(lvw);
    }
}
