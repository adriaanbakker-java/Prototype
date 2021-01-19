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

@Controller
public class GUIController {
    @Autowired
    private VoorwaardenbeheerService voorwaardenbeheerService;

    @Autowired
    private LeveringsvoorwaardenRepository leveringsvoorwaardenRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PageRepository pageRepository;


    @GetMapping("/initbookdb")
    public String initBookDB(Model model) {
        System.out.println("init the book db");
        Book book = new Book("Java world", "John Doe", "1234567");
        bookRepository.save(book);
        Page page = new Page(1, "Introduction contents", "Introduction", book);
        pageRepository.save(page);
        pageRepository.save(new Page(65, "Java 8 contents", "Java 8", book));
        pageRepository.save(new Page(95, "Concurrency contents", "Concurrency", book));

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
        voorwaardenbeheerService.addVoorwaarde(voorwaardeDto);
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