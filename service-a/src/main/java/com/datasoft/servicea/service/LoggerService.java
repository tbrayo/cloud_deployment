package com.datasoft.servicea.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class LoggerService extends HandlerInterceptorAdapter{

    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("remoteIP", String.valueOf(request.getRemoteAddr()));
        log.info("----------------------------------------------------------------");
        log.info("-------------------User Activity Logger Begins------------------");
        log.info("Remote address: {}", request.getRemoteAddr());
        log.info("Device ID: {}", request.getHeader("di"));
        log.info("Current Context Path: {}", request.getRequestURL().toString());
        log.info("Actual Context Path: {}", request.getHeader("actualContext"));
        log.info("*****************User Activity Logger Ends***********************");
        log.info("*****************************************************************");

        return true;
    }

//    public void log(HttpServletRequest request) {
//        log.info("***************************************************************");
//        log.info("Request URI and method: {} {}", request.getRequestURI(), request.getMethod());
//        log.info("Request URL: {}", request.getRequestURL());
//        log.info("From: {}", request.getRemoteAddr());
//        log.info("Content Type: {}",request.getContentType());
//        log.info("Remote Host: {}",request.getRemoteHost());
//        log.info("***************************************************************");
//    }
    public void log() {
        log.info("***************************************************************");
        log.info("Request URI: {} and method: {}", request.getRequestURI(), request.getMethod());
        log.info("Request URL: {}", request.getRequestURL());
        log.info("From: {}", request.getRemoteAddr());
        log.info("Content Type: {}",request.getContentType());
        log.info("Remote Host: {}",request.getRemoteHost());
        log.info("***************************************************************");
    }
}