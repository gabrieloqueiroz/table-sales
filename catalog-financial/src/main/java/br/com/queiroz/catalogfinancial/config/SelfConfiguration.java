package br.com.queiroz.catalogfinancial.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class SelfConfiguration {

  @Bean
  public ModelMapper getModelMapper(){
    return new ModelMapper();
  }
}
