package com.datasoft.servicea.model;


import lombok.Data;

import java.util.Date;
@Data
public class ApiInfo {
   private Integer id;
   private String serviceName;
   private String apiEndpoint;
   private String headers;
   private String body;
   private Integer responseCode;
   private String remoteIp;
   private String destinationIp;
   private Date date;
}