package com.example.leveringsvw.controller;


import com.example.leveringsvw.model.DogDto;
import com.example.leveringsvw.repo.Dog;
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
public class ExampleController {
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
        model.addAttribute("dog", new Dog());
        return "toevoegen_regel";
    }


    @PostMapping("/toevoegen_regel")
    public String addDogSubmit(@ModelAttribute Dog dog) {
        DogDto dogdto = new DogDto();
        dogdto.setAge(dog.getAge());
        dogdto.setId(dog.getId());
        dogdto.setName(dog.getName());
        dogsService.add(dogdto);
        return "toevoegen_regel_resultaat";
    }


    @GetMapping("/delete_dog")
    public String deleteDog(Model model) {
        model.addAttribute("idMessage", new IdMessage());
        return "delete_dog";
    }

    @PostMapping("/delete_dog")
    public String deleteSubmit(Model model, @ModelAttribute IdMessage idMessage) {
        Dog dog = new Dog();

        model.addAttribute("dog", dog);
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