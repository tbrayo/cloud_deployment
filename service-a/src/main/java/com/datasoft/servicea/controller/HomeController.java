package com.datasoft.servicea.controller;

import com.datasoft.servicea.model.ApiInfo;
import com.datasoft.servicea.model.Country;
import com.datasoft.servicea.service.ApiInfoService;
import com.datasoft.servicea.service.LoggerService;
import com.google.gson.Gson;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

//    @GetMapping("/")
//    public String home(){
//        loggerService.log();
//        return "Welcome to the Java Rest Service";
//    }

    @GetMapping("/")
    public String home(HttpServletRequest request){
        long startTime = System.currentTimeMillis();
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setServiceName(applicationName);
        apiInfo.setRemoteIp(request.getRemoteAddr());
        apiInfo.setApiEndpoint(request.getRequestURI());
        apiInfoService.add(apiInfo);
        long endTime = System.currentTimeMillis();
        log.info("Elapsed Time : {}",endTime - startTime);
        return "Welcome to the Java Rest Service and Time taken to respond is "+ String.valueOf(endTime -startTime) ;
    }


    @GetMapping("/simulator")
    public String simulatorStatus(){
        loggerService.log();
        RestTemplate restTemplate = new RestTemplate();
        String simulatorResourceUrl = "http://localhost:8081/";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(simulatorResourceUrl,String.class);
        log.info("Response Status: {} Response Body: {}",responseEntity.getStatusCode(),responseEntity.getBody());
        Gson gson = new Gson();
        return gson.toJson("Simulation service is running on the background",String.class);
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
    public ModelAndView redirectPostToPost(HttpServletRequest request,@RequestBody String str) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setRemoteIp(request.getRemoteAddr());
        apiInfo.setApiEndpoint(request.getRequestURI());
        apiInfoService.add(apiInfo);
        return new ModelAndView("redirect:/redirectedPostToPost");
    }

    @PostMapping(value = "/redirectToAnotherApi",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView redirectToPost(HttpServletRequest request, @RequestBody Map<String,String> data, final RedirectAttributes redirectAttributes) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE,HttpStatus.TEMPORARY_REDIRECT);
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setRemoteIp(request.getRemoteAddr());
        apiInfo.setApiEndpoint(request.getRequestURI());
        apiInfoService.add(apiInfo);
        return new ModelAndView("redirect:/redirectedPostToPost") ;
    }

    @PostMapping(value = "/redirectedPostToPost",produces = MediaType.APPLICATION_JSON_VALUE)
    public String redirectedPostToPost(HttpServletRequest request) {
        loggerService.log();
        log.info("Method Name: {}",request.getRequestURI());
        System.out.println("Received Request ");
        Gson gson = new Gson();
        return gson.toJson("redirection is Done for Post Method",String.class);
    }


    @GetMapping("/redirectGetToGET")
    public ModelAndView redirectGetToGET(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        log.info("HttpStatus: {}",HttpStatus.TEMPORARY_REDIRECT);
        loggerService.log();
        return new ModelAndView("redirect:/redirectedGetToGET");

    }

    @GetMapping("/redirectedGetToGET")
    public String redirectedGetToGET() {
        loggerService.log();
        log.info("Redirected From GET To GET");
        return new Gson().toJson("Service has been redirected to another api and business successfully processed",String.class);
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