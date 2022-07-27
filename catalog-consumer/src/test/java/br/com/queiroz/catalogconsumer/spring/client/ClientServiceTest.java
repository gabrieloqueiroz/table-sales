package br.com.queiroz.catalogconsumer.spring.client;

import br.com.queiroz.catalogconsumer.spring.ConsumerApplication;
import br.com.queiroz.catalogconsumer.spring.configuration.SelfConfiguration;
import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.util.FullDetailMother;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.QueueDispatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(classes = { ConsumerApplication.class}, webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientServiceTest {

  private static MockWebServer webServerDetail;
  private static MockWebServer webServerFinancial;

  private static MockWebServer mockServer;
  public static String serverBaseUrl;
  @MockBean
  private SelfConfiguration selfConfiguration;
  @Autowired
  private ClientService clientService;
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @BeforeEach
  public void setUp() throws IOException {
    mockServer = new MockWebServer();
    mockServer.setDispatcher(new QueueDispatcher());
    mockServer.start();
    serverBaseUrl = "http://127.0.0.1:" + mockServer.getPort();
    when(selfConfiguration.getUrlDetailService()).thenReturn(serverBaseUrl);
    when(selfConfiguration.getUrlFinancialService()).thenReturn(serverBaseUrl);
  }

  @AfterEach
  public void stopAll() throws IOException {
    webServerDetail.close();
    webServerFinancial.close();
    mockServer.shutdown();
  }

  @Test
  public void should_return_product_with_price() throws JsonProcessingException {
    //Given
    FullDetailDto detailDto = FullDetailMother.getDetailDto();
    FullDetailDto financialDto = FullDetailMother.getFinancialDto();

    mockServer.enqueue(new MockResponse().addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .setBody(OBJECT_MAPPER.writeValueAsString(detailDto)).setResponseCode(HttpStatus.OK.value()));

    mockServer.enqueue(new MockResponse().addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .setBody(OBJECT_MAPPER.writeValueAsString(financialDto)).setResponseCode(HttpStatus.OK.value()));

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
