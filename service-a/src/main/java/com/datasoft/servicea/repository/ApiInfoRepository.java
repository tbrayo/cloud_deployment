package com.datasoft.servicea.repository;

import com.datasoft.servicea.model.ApiInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Slf4j
@Repository
public class ApiInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiInfo add(ApiInfo apiInfo){
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("api_info")
                .usingGeneratedKeyColumns("id");

        HashMap<String,Object> parameterData = new HashMap<>();
        parameterData.put("service_name",apiInfo.getServiceName());
        parameterData.put("api_endpoint",apiInfo.getApiEndpoint());
        parameterData.put("headers",apiInfo.getHeaders());
        parameterData.put("body",apiInfo.getBody());
        parameterData.put("response_code",apiInfo.getResponseCode());
        parameterData.put("remote_ip",apiInfo.getRemoteIp());
        parameterData.put("destination_ip",apiInfo.getDestinationIp());
        parameterData.put("date",apiInfo.getDate());
        try{
            Number autoGenId = jdbcInsert.executeAndReturnKey(parameterData);
            if(autoGenId !=null){
                apiInfo.setId(autoGenId.intValue());
                return apiInfo;
            }
        }catch (DataAccessException dae){
            log.error("Api Information Adding Failed, Error: {}",dae.getLocalizedMessage());
            return apiInfo;
        }
        return apiInfo;
    }
}