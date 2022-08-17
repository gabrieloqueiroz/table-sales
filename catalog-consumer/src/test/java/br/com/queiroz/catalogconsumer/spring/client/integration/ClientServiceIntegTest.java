package br.com.queiroz.catalogconsumer.spring.client.integration;

import br.com.queiroz.catalogconsumer.spring.ConsumerApplication;
import br.com.queiroz.catalogconsumer.spring.application.ConsumerApplicationTestConfiguration;
import br.com.queiroz.catalogconsumer.spring.configuration.SelfConfiguration;
import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.subordinated.config.DetailServiceConfiguration;
import br.om.queiroz.utils.ConstantsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableAutoConfiguration(exclude={ DataSourceAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@ComponentScan("br.com.queiroz.catalogconsumer.spring.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ConsumerApplication.class, ConsumerApplicationTestConfiguration.class})
public class ClientServiceIntegTest {

  @Autowired
  private WebTestClient webTest;

  @BeforeEach
  public void setUp(){
    webTest.mutate().responseTimeout(Duration.ofMillis(300000)).build();
  }

  @Test
  public void should_return_product_with_price(){
    //Given

    //When
    FullDetailDto response = this.webTest
        .get()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_CONSUMER_SERVICE + ConstantsUtils.PATH_SEPARATOR + 1L).exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(FullDetailDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);

  }
}
