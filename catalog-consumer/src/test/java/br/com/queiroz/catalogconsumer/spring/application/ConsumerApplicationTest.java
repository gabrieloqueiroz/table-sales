package br.com.queiroz.catalogconsumer.spring.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@EnableAutoConfiguration(exclude={ DataSourceAutoConfiguration.class})
public class ConsumerApplicationTest {

  @Test
  public void contextLoads() {
  }
}
