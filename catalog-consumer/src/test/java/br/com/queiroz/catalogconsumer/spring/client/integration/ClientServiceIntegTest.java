package br.com.queiroz.catalogconsumer.spring.client.integration;

import br.com.queiroz.catalogconsumer.spring.ConsumerApplication;
import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.om.queiroz.utils.ConstantsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@Component("br.com.queiroz.catalogconsumer.spring.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ConsumerApplication.class})
public class ClientServiceIntegTest {

  @Autowired
  WebTestClient webTestClientDetail;
  @Autowired
  WebTestClient webTestClientFinancial;

  @BeforeEach
  public void setUp(){
    webTestClientDetail.mutate().responseTimeout(Duration.ofMillis(300000)).build();
    webTestClientFinancial.mutate().responseTimeout(Duration.ofMillis(300000)).build();
  }

  @Test
  public void should_return_product_with_price(){
    //Given
    Long requestId = 1L;

    //When
    FullDetailDto response = webTestClientDetail
        .get()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_CONSUMER_SERVICE + ConstantsUtils.PATH_SEPARATOR + "{requestId}", requestId).exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(FullDetailDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);

  }
}
