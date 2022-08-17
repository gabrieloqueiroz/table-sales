package br.com.queiroz.catalogdetail.controller;

import br.com.queiroz.catalogdetail.CatalogApplication;
import br.com.queiroz.catalogdetail.config.SelfConfiguration;
import br.com.queiroz.catalogdetail.dto.DetailDto;
import br.com.queiroz.catalogdetail.util.DetailDtoMother;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.queiroz.catalogdetail.util.DetailDtoMother.getDetailDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@ComponentScan("br.com.queiroz.catalogdetail.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { CatalogApplication.class, SelfConfiguration.class})
public class DetailControllerTest {

  @Autowired
  private WebTestClient webTest;

  @BeforeEach
  public void setUp(){
    webTest.mutate().responseTimeout(Duration.ofMillis(300000)).build();
  }

  @Test
  public void should_return_detail_by_id(){
    //Given

    //When
    DetailDto response = this.webTest
        .get()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_DETAIL_SERVICE + ConstantsUtils.PATH_SEPARATOR + 1L).exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(DetailDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);
  }

  @Test
  public void should_create_detail(){
    //Given
    DetailDto request = getDetailDto();

    //When
    DetailDto response = this.webTest
        .post()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_DETAIL_SERVICE)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBody(DetailDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);
    assertEquals(request.getId(), response.getId());
    assertEquals(request.getColor(), response.getColor());
    assertEquals(request.getDescription(), response.getDescription());
    assertEquals(request.getManual(), response.getManual());
  }

  @Test
  public void should_return_details_by_range_ids(){
    //Given
    List<Long> request = new ArrayList<>(Arrays.asList(1L, 3L));

    //When
    List<DetailDto> response = this.webTest
        .post()
        .uri(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_DETAIL_SERVICE + ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_DETAIL_LIST_IDS)
        .body(BodyInserters.fromValue(request))
        .exchange()
        .expectStatus().is2xxSuccessful()
        .expectBodyList(DetailDto.class)
        .returnResult()
        .getResponseBody();

    //Then
    assertNotNull(response);
    assertEquals(2, response.size());
  }
}
