package com.datasoft.simulatorservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class HomeController {

    @Value("${service.url}")
    private String serviceUrl;

    private final RestTemplate restTemplate;

    public HomeController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/")
    public String home(){
        return "Welcome to the simulator service";
    }

    @GetMapping("/servicea")
    public String getFirstServiceName(){
        return this.restTemplate.getForObject(serviceUrl,String.class);
    }


}