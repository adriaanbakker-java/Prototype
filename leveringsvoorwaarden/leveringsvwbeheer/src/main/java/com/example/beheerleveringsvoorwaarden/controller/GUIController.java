package com.example.beheerleveringsvoorwaarden.controller;


import com.example.beheerleveringsvoorwaarden.model.*;
import com.example.beheerleveringsvoorwaarden.repo.*;
import com.example.beheerleveringsvoorwaarden.service.VoorwaardenbeheerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class GUIController {
    @Autowired
    private VoorwaardenbeheerService voorwaardenbeheerService;

    @Autowired
    private LeveringsvoorwaardenRepository leveringsvoorwaardenRepository;

    @Autowired
    private BerichtgegevenRepository berichtgegevenRepository;

    @Autowired
    private BerichtenRepository berichtenRepository;


    @GetMapping("/initdb")
    public String initDb(Model model) {
        System.out.println("init the db");
        voorwaardenbeheerService.initDb();



        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/getmessage")
    public String greetingForm(Model model) {
        model.addAttribute("idMessage", new IdMessage());
        return "getmessage";
    }

    @PostMapping("/getmessage")
    public String greetingSubmit(@ModelAttribute IdMessage idMessage) {
        return "showmessage";
    }


    @GetMapping("/toevoegen_leveringsvoorwaarde")
    public String toevoegenLeveringsVoorwaarde(Model model) {

        model.addAttribute( "berichten", voorwaardenbeheerService.getLijstBerichten());
        return "toevoegen_leveringsvoorwaarde";
    }

    @PostMapping("/toevoegen_leveringsvoorwaarde")
    public String toevoegenLeveringsVoorwaardePost(Model model, @ModelAttribute Getstring getstring) {
        System.out.println(getstring.getContent());
        Long berichtId = Long.parseLong(getstring.getContent());
        Optional<Bericht> optionalBericht = berichtenRepository.findById(berichtId);
        optionalBericht.ifPresent(bericht -> {
            model.addAttribute("bericht", bericht);
            List<Berichtgegeven> berichtgegevens = berichtgegevenRepository.findByBerichtId(berichtId);
            model.addAttribute("berichtgegevens", berichtgegevens);
            model.addAttribute( "leveringsdoelen", voorwaardenbeheerService.getLijstLeveringsdoelen());
            model.addAttribute("berichten", berichtenRepository.getListBerichtenGesorteerd());
        });
        return "toevoegen_leveringsvoorwaarde_bericht";
    }

    @PostMapping("/toevoegen_leveringsvoorwaarde_submit")
    public String toevoegenLeveringsVoorwaardeSubmit(Model model, @ModelAttribute  LeveringsvoorwaardeDto dto)
    throws Exception {
        voorwaardenbeheerService.addLeveringsvoorwaarde(dto);
        return "index";
    }

    @GetMapping("/delete_voorwaarde")
    public String deleteVoorwaarde(Model model) {
        model.addAttribute("idMessage", new IdMessage());
        return "delete_voorwaarde";
    }

    @PostMapping("/delete_voorwaarde")
    public String deleteVoorwaardeSubmit(Model model, @ModelAttribute IdMessage idMessage) {
        Voorwaarde voorwaarde = new Voorwaarde();
        model.addAttribute("idMessage", idMessage);
        long idVoorwaarde = Integer.parseInt(idMessage.getContent());
        voorwaardenbeheerService.deleteLeveringsvoorwaarde(idVoorwaarde);
        return "delete_voorwaarde_confirm";
    }

    @RequestMapping("/list")
    public String countsList(Model model, @ModelAttribute Filter filter, @ModelAttribute IdMessage idMessage ) {
        String berichtnaam = "";
        String leveringsdoel = "";
        if (filter.getBerichtnaam() != null) {
            berichtnaam = filter.getBerichtnaam();
        }
        if (filter.getLeveringsdoel() != null) {
            leveringsdoel = filter.getLeveringsdoel();
        }
        List<Voorwaarde> voorwaarden =  voorwaardenbeheerService.getLijstLeveringsvoorwaarden(berichtnaam, leveringsdoel);
        model.addAttribute("counts", voorwaarden);
        model.addAttribute( "berichten", voorwaardenbeheerService.getLijstBerichten());
        model.addAttribute( "leveringsdoelen", voorwaardenbeheerService.getLijstLeveringsdoelen());
        model.addAttribute("berichtnaam", filter);
        return "list";
    }

}