package com.example.leveringsvw.controller;


import com.example.leveringsvw.model.VoorwaardeDto;
import com.example.leveringsvw.repo.Voorwaarde;
import com.example.leveringsvw.service.DogsService;
import com.example.leveringsvw.model.IdMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GUIController {
    @Autowired
    private DogsService dogsService;

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

    @GetMapping("/toevoegen_regel")
    public String addDog(Model model) {
        model.addAttribute("voorwaarde", new Voorwaarde());
        return "toevoegen_regel";
    }


    @PostMapping("/toevoegen_regel")
    public String addDogSubmit(@ModelAttribute Voorwaarde voorwaarde) {
        VoorwaardeDto voorwaardeDto = new VoorwaardeDto();
        voorwaardeDto.setAge(voorwaarde.getAge());
        voorwaardeDto.setId(voorwaarde.getId());
        voorwaardeDto.setBerichtnaam(voorwaarde.getName());
        dogsService.add(voorwaardeDto);
        return "toevoegen_regel_resultaat";
    }


    @GetMapping("/delete_dog")
    public String deleteDog(Model model) {
        model.addAttribute("idMessage", new IdMessage());
        return "delete_dog";
    }

    @PostMapping("/delete_dog")
    public String deleteSubmit(Model model, @ModelAttribute IdMessage idMessage) {
        Voorwaarde voorwaarde = new Voorwaarde();

        model.addAttribute("voorwaarde", voorwaarde);
        model.addAttribute("idMessage", idMessage);
        return "delete_dog_confirm";
    }

    @RequestMapping("/list")
    public String countsList(Model model) {
        model.addAttribute("counts", dogsService.getDogs());
        return "list";
    }

    @RequestMapping("/olddogs")
    public String countsOldList(Model model) {
        model.addAttribute("counts", dogsService.getOldDogs());
        return "list";
    }



}