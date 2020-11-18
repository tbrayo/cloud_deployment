package com.datasoft.servicea.service;

import com.datasoft.servicea.model.ApiInfo;
import com.datasoft.servicea.repository.ApiInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiInfoService {

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    public ApiInfo add(ApiInfo apiInfo){
        apiInfo.setBody(apiInfo.getBody());
        apiInfoRepository.add(apiInfo);
        return apiInfo;
    }
}