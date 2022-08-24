package br.com.queiroz.catalogfinancial.controller.integ;

import br.com.queiroz.catalogfinancial.FinancialApplication;
import br.com.queiroz.catalogfinancial.config.SelfConfiguration;
import br.com.queiroz.catalogfinancial.dto.FinancialDto;
import br.com.queiroz.catalogfinancial.util.FinancialMotherTest;
import br.om.queiroz.utils.ConstantsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@ComponentScan("br.com.queiroz.catalogfinancial.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { FinancialApplication.class, SelfConfiguration.class})
public class FinancialControllerIntegTest {

  @Autowired
  private WebTestClient webTest;

  @BeforeEach
  public void setup(){
    webTest.mutate().responseTimeout(Duration.ofMillis(300000)).build();
  }

  @Test
  public void should_return_financial_by_id(){
    //Given
    Long requestId = 1L;

    //When
    FinancialDto response = webTest
        .get()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_FINANCIAL_SERVICE + ConstantsUtils.PATH_SEPARATOR + requestId)
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(FinancialDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);
  }

  @Test
  public void should_return_financials_by_range(){
    //Given
    String min = "65", max = "95";

    //When
    List<FinancialDto> response = webTest
        .get()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_FINANCIAL_SERVICE + ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_FINANCIAL_BY_RANGE + "?min=" + min + "&max=" + max)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(FinancialDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);
  }

  @Test
  public void should_create_financial(){
    //Given
    FinancialDto request = FinancialMotherTest.getFinancialDto();

    //When
    FinancialDto response = webTest
        .post()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_FINANCIAL_SERVICE)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(FinancialDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);
  }
}
