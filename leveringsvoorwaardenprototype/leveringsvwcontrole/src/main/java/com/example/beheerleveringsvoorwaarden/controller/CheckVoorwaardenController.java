package com.example.beheerleveringsvoorwaarden.controller;

import com.example.checkleveringsvoorwaarden.model.Answer;
import com.example.checkleveringsvoorwaarden.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.beheerleveringsvoorwaarden.service.VoorwaardencontroleService;

@ResponseBody
@Controller
public class CheckVoorwaardenController {
    @Autowired
    private VoorwaardencontroleService voorwaardencontroleService;

    @PostMapping("/checkvoorwaarden")
    public Answer checkVoorwaarden(@RequestBody Request request) {
        return voorwaardencontroleService.checkRequest(request);
    }

}
