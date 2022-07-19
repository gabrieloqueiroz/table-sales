package br.com.queiroz.catalogconsumer.subordinated.webservices.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

  public String getDetailUrl(){
    return "http://localhost:8080/detail";
  }

  public String getFinancialUrl(){
    return "http://localhost:8081/financial";
  }
}
