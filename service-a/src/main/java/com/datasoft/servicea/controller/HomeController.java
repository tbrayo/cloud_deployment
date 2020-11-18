package com.datasoft.servicea.controller;

import com.datasoft.servicea.model.ApiInfo;
import com.datasoft.servicea.model.Country;
import com.datasoft.servicea.service.ApiInfoService;
import com.datasoft.servicea.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private LoggerService loggerService;

    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping(value = "/testJson",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Country> testGsonProduction() {
        Country country = new Country();
        country.setName("Australia");
        country.setIsStateExist(true);
        country.setId(1);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }
    @RequestMapping(value = "/testJsonConsumption",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Country> testGsonConsumption(@RequestBody Country country) {
        System.out.println("Country Name: " +country.getName());
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @GetMapping("/")
    public String home(){
        loggerService.log();
        return "Welcome to the Java Rest Service";
    }

    @GetMapping("/simulator")
    public String simulatorStatus(){
        loggerService.log();
        RestTemplate restTemplate = new RestTemplate();
        String simulatorResourceUrl = "http://localhost:8081/";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(simulatorResourceUrl,String.class);
        log.info("Response Status: {} Response Body: {}",responseEntity.getStatusCode(),responseEntity.getBody());
        return responseEntity.getBody()  ;
    }
    @PostMapping("/addInfo")
    public ApiInfo addApiInfo(HttpServletRequest request,@RequestBody ApiInfo apiInfo){
        apiInfo.setServiceName(applicationName);
        apiInfo.setRemoteIp(request.getRemoteAddr());
        apiInfo.setApiEndpoint(request.getRequestURI());
        apiInfo.setHeaders(request.getHeader("di"));
        loggerService.log();
        return apiInfoService.add(apiInfo);
    }

    @PostMapping("/redirectPostToPost")
    public ModelAndView redirectPostToPost(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        loggerService.log();
        log.info("Method Name: {}",request.getRequestURI());
        return new ModelAndView("redirect:/redirectedPostToPost");
    }

    @PostMapping(value = "/redirectedPostToPost",produces = MediaType.APPLICATION_JSON_VALUE)
    public String redirectedPostToPost(HttpServletRequest request) {
        loggerService.log();
        log.info("Method Name: {}",request.getRequestURI());
        System.out.println("Received Request ");
        return "redirection Done for Post Method";
    }

    @GetMapping("/redirectPostToGET")
    public ModelAndView redirectPostToGET(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        loggerService.log();
        return new ModelAndView("redirect:/redirectedPostToGET");
    }

    @GetMapping("/redirectedPostToGET")
    public String redirectedPostToGET() {
        loggerService.log();
        System.out.println("Redirected From Post To GET");
        return "redirection";
    }
}