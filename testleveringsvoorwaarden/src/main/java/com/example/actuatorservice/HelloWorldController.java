package com.example.actuatorservice;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicInteger counter = new AtomicInteger();

    int count = 0;

    @GetMapping("/request01")
    @ResponseBody
    public Greeting request01(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        String answer = "fixed answer:" + name;
        //return new Greeting(counter.incrementAndGet(), String.format(template, name));
        if (counter.incrementAndGet() % 2 == 1) {
             answer = getAnswerFromAnswerService(name);
            System.out.println("even count");
        } else {
            System.out.println("non-even count");
        }
        return new Greeting(counter.get(), "answer to request01:" + answer);
    }

    @GetMapping("/request02")
    @ResponseBody
    public Greeting getAnswerFromAnswerService1(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), "answer to request02" +  String.format(template, name));
    }

    private String getAnswerFromAnswerService(String name)
    {
        final String uri = "http://localhost:8082/bronrequest?name="+name;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
        return result;
    }
}
