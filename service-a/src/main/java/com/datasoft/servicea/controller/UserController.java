package com.datasoft.servicea.controller;

import com.datasoft.servicea.model.ApiInfo;
import com.datasoft.servicea.service.ApiInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private ApiInfoService apiInfoService;

    @PostMapping(value = "/user")
    public String addUser(HttpServletRequest request, @RequestBody Map<String,String> data){
        log.info("Data Received: {}",data.toString());
        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setBody(String.valueOf(data));
        String head = request.getHeader("head");
        if(head !=null) {
            apiInfo.setHeaders(head);
        }
        apiInfo.setApiEndpoint(request.getRequestURI());
        apiInfo.setRemoteIp(request.getRemoteAddr());
        apiInfoService.add(apiInfo);
        return "user Added";
    }
}