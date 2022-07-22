package br.com.queiroz.catalogconsumer.spring.client;

import br.com.queiroz.catalogconsumer.spring.ConsumerApplication;
import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.util.FullDetailMother;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.QueueDispatcher;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(classes = { ConsumerApplication.class}, webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientServiceTest {

  private static MockWebServer webServerDetail;
  private static MockWebServer webServerFinancial;

  @Autowired
  private ClientService clientService;
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @BeforeAll
  public static void setUpServer() throws IOException {
    webServerDetail = new MockWebServer();
    webServerDetail.start(8080);
    webServerFinancial = new MockWebServer();
    webServerFinancial.start(8081);
  }

  @AfterAll
  public static void stopAll() throws IOException {
    webServerDetail.close();
    webServerFinancial.close();
  }

  @BeforeEach
  public void setUp(){
    webServerDetail.setDispatcher(new QueueDispatcher());
    String webServerUrlDetail ="http://localhost:" + webServerDetail.getPort();
    webServerFinancial.setDispatcher(new QueueDispatcher());
    String webServerUrlFinancial ="http://localhost:" + webServerFinancial.getPort();
  }

  @Test
  public void should_return_product_with_price() throws JsonProcessingException {
    //Given
    FullDetailDto detailDto = FullDetailMother.getDetailDto();
    FullDetailDto financialDto = FullDetailMother.getFinancialDto();

    webServerDetail.enqueue(new MockResponse().setResponseCode(200).setBody(OBJECT_MAPPER.writeValueAsString(detailDto))
        .addHeader("Content-Type", "application/json"));

    webServerFinancial.enqueue(new MockResponse().setResponseCode(200).setBody(OBJECT_MAPPER.writeValueAsString(financialDto))
        .addHeader("Content-Type", "application/json"));


    //When
    FullDetailDto response = clientService.getProductWithPrice(1L);

    //Then
    assertNotNull(response);
    assertEquals(detailDto.getColor(), response.getColor());
    assertEquals(detailDto.getDescription(), response.getDescription());
    assertEquals(detailDto.getManual(), response.getManual());
    assertEquals(detailDto.getId(), response.getId());
    assertEquals(financialDto.getSalePrice(), response.getSalePrice());
    assertEquals(financialDto.getId(), response.getId());
  }

  @Test
  public void should_create_product_with_price() throws JsonProcessingException {
    //Given
    FullDetailDto expectedFullDto = FullDetailMother.getFullDetailDtoMother().get(0);
    FullDetailDto detailDto = FullDetailMother.getDetailDto();
    FullDetailDto financialDto = FullDetailMother.getFinancialDto();

    webServerDetail.enqueue(new MockResponse().setResponseCode(200).setBody(OBJECT_MAPPER.writeValueAsString(detailDto))
        .addHeader("Content-Type", "application/json"));

    webServerFinancial.enqueue(new MockResponse().setResponseCode(200).setBody(OBJECT_MAPPER.writeValueAsString(financialDto))
        .addHeader("Content-Type", "application/json"));

    //When
    FullDetailDto response = clientService.createProductWithPrice(expectedFullDto);

    //Then
    assertNotNull(response);
    assertEquals(expectedFullDto.getId(), response.getId());
    assertEquals(expectedFullDto.getSalePrice(), response.getSalePrice());
    assertEquals(expectedFullDto.getPurchasePrice(), response.getPurchasePrice());
    assertEquals(expectedFullDto.getDescription(), response.getDescription());
    assertEquals(expectedFullDto.getManual(), response.getManual());
    assertEquals(expectedFullDto.getColor(), response.getColor());
  }

  @Test
  public void should_return_financial_by_range_of_price() throws JsonProcessingException {
    //Given
    List<FullDetailDto> expectedFinancial =
        Arrays.asList(FullDetailMother.getFinancialDto(), FullDetailMother.getFinancialDto(), FullDetailMother.getFinancialDto()) ;
    BigDecimal min = new BigDecimal(90);
    BigDecimal max = new BigDecimal(200);

    webServerFinancial.enqueue(new MockResponse().setResponseCode(200).setBody(OBJECT_MAPPER.writeValueAsString(expectedFinancial))
        .addHeader("Content-Type", "application/json"));

    //When
    List<FullDetailDto> response = clientService.getFinancialByRange(min, max);

    //Then
    assertNotNull(response);
    assertEquals(expectedFinancial.size(), response.size());
  }

  @Test
  public void should_return_details_by_list_ids() throws JsonProcessingException {
    //Given
    List<FullDetailDto> expectedDetails =
        Arrays.asList(FullDetailMother.getDetailDto(), FullDetailMother.getDetailDto(), FullDetailMother.getDetailDto());

    webServerDetail.enqueue(new MockResponse().setResponseCode(200).setBody(OBJECT_MAPPER.writeValueAsString(expectedDetails))
        .addHeader("Content-Type", "application/json"));

    //When
    List<FullDetailDto> response = clientService.getDetailById(expectedDetails);

    //Then
    assertNotNull(response);
    assertEquals(expectedDetails.size(), response.size());
  }
}
