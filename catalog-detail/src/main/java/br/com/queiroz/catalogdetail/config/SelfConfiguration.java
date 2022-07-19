package br.com.queiroz.catalogdetail.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelfConfiguration {

  @Bean
  public ModelMapper getModelMapper(){
    return new ModelMapper();
  }
}
