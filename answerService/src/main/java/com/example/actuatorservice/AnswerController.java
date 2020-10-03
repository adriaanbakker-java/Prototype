package com.example.actuatorservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnswerController {

    private static final String template = "answer-service geeft waarde bij vraag:[%s]";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/bronrequest")
    @ResponseBody
    public Greeting getAnswerFromAnswerService1(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }



}
