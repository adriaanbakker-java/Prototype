package com.example.beheerleveringsvoorwaarden.controller;


import com.example.beheerleveringsvoorwaarden.model.Filter;
import com.example.beheerleveringsvoorwaarden.model.VoorwaardeDto;
import com.example.beheerleveringsvoorwaarden.repo.Bericht;
import com.example.beheerleveringsvoorwaarden.repo.Voorwaarde;
import com.example.beheerleveringsvoorwaarden.service.VoorwaardenbeheerService;
import com.example.beheerleveringsvoorwaarden.model.IdMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GUIController {
    @Autowired
    private VoorwaardenbeheerService voorwaardenbeheerService;


    @PostMapping("/check")
    public void check() {
        System.out.println("check voorwaarden");
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

    @GetMapping("/toevoegen_voorwaarde")
    public String toevoegenVoorwaarde(Model model) {
        model.addAttribute("voorwaarde", new Voorwaarde());
        model.addAttribute( "berichten", voorwaardenbeheerService.getLijstBerichten());
        model.addAttribute( "leveringsdoelen", voorwaardenbeheerService.getLijstLeveringsdoelen());
        return "toevoegen_voorwaarde";
    }


    @PostMapping("/toevoegen_voorwaarde")
    public String toevoegenVoorwaardeSubmit(@ModelAttribute Voorwaarde voorwaarde) {
        VoorwaardeDto voorwaardeDto = new VoorwaardeDto();
        voorwaardeDto.setLeveringsdoel(voorwaarde.getLeveringsdoel());
        voorwaardeDto.setId(voorwaarde.getId());
        voorwaardeDto.setBerichtnaam(voorwaarde.getBerichtnaam());
        voorwaardeDto.setPadnaargegeven(voorwaarde.getPadnaargegeven());
        voorwaardenbeheerService.add(voorwaardeDto);
        return "toevoegen_voorwaarde_resultaat";
    }


    @GetMapping("/delete_voorwaarde")
    public String deleteVoorwaarde(Model model) {
        model.addAttribute("idMessage", new IdMessage());
        return "delete_voorwaarde";
    }

    @PostMapping("/delete_voorwaarde")
    public String deleteVoorwaardeSubmit(Model model, @ModelAttribute IdMessage idMessage) {
        Voorwaarde voorwaarde = new Voorwaarde();

        model.addAttribute("voorwaarde", voorwaarde);
        model.addAttribute("idMessage", idMessage);
        return "delete_voorwaarde_confirm";
    }

    @RequestMapping("/list")
    public String countsList(Model model, @ModelAttribute Filter filter ) {

        /*List<Voorwaarde> voorwaardenlijst = (List<Voorwaarde>) voorwaardenbeheerService.getLijstVoorwaardenGesorteerd();
        List<Voorwaarde> voorwaarden = new ArrayList<>();
        if (filter == null) {
            voorwaarden = voorwaardenlijst;
        } else if (filter.getBerichtnaam() == null) {
            voorwaarden = voorwaardenlijst;
        } else if (filter.getBerichtnaam().equals("")) {
            voorwaarden = voorwaardenlijst;
        } else {
                for (Voorwaarde v: voorwaardenlijst) {
                    if (v.getBerichtnaam().equals(filter.getBerichtnaam())) {
                        voorwaarden.add(v);
                    }
                }
        }*/
        String berichtnaam = "";
        String leveringsdoel = "";
        if (filter.getBerichtnaam() != null) {
            berichtnaam = filter.getBerichtnaam();
        }
        if (filter.getLeveringsdoel() != null) {
            leveringsdoel = filter.getLeveringsdoel();
        }
        List<Voorwaarde> voorwaarden =  voorwaardenbeheerService.getLijstVoorwaardenGesorteerdGefilterd(berichtnaam, leveringsdoel);
        model.addAttribute("counts", voorwaarden);
        model.addAttribute( "berichten", voorwaardenbeheerService.getLijstBerichten());
        model.addAttribute( "leveringsdoelen", voorwaardenbeheerService.getLijstLeveringsdoelen());
        model.addAttribute("berichtnaam", filter);
        return "list";
    }

//    @RequestMapping("/listsorted")
//    public String listSorted(Model model) {
//        model.addAttribute("counts", voorwaardenbeheerService.getLijstVoorwaardenGesorteerd());
//        model.addAttribute( "berichten", voorwaardenbeheerService.getLijstBerichten());
//        return "list";
//    }



}