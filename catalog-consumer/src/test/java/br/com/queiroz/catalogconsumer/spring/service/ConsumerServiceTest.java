package br.com.queiroz.catalogconsumer.spring.service;

import br.com.queiroz.catalogconsumer.spring.client.ClientService;
import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.util.FullDetailMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static br.com.queiroz.catalogconsumer.spring.util.FullDetailMother.getDetailDto;
import static br.com.queiroz.catalogconsumer.spring.util.FullDetailMother.getFinancialDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsumerServiceTest {

  private ConsumerService consumerService;
  private ClientService clientService;

  @BeforeEach
  public void setUp(){
    this.clientService = mock(ClientService.class);
    this.consumerService = new ConsumerService(this.clientService);
  }

  @Test
  public void should_return_full_product(){
    //Given

    Long requestId = 1L;
    FullDetailDto expectedFullDto = FullDetailMother.getFullDetailDtoMother().get(0);

    when(clientService.getProductWithPrice(any())).thenReturn(expectedFullDto);

    //When

    FullDetailDto response = consumerService.getFullDetail(requestId);

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
  public void should_create_full_product(){
    //Given
    FullDetailDto expectedFullDto = FullDetailMother.getFullDetailDtoMother().get(0);

    when(clientService.createProductWithPrice(any())).thenReturn(expectedFullDto);

    //When
    FullDetailDto response = consumerService.createProduct(expectedFullDto);

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
  public void should_return_products_by_range(){
    //Given
    BigDecimal min = new BigDecimal(90);
    BigDecimal max = new BigDecimal(200);
    List<FullDetailDto> expectedFinancialDto = Arrays.asList(getFinancialDto(), getFinancialDto(), getFinancialDto());
    List<FullDetailDto> expectedDetailDto = Arrays.asList(getDetailDto(), getDetailDto(), getDetailDto());

    when(clientService.getFinancialByRange(any(), any())).thenReturn(expectedFinancialDto);
    when(clientService.getDetailById(any())).thenReturn(expectedDetailDto);

    //When
    List<FullDetailDto> response = consumerService.getFinancialByRange(min, max);

    //Then
    assertNotNull(response);
    assertEquals(expectedDetailDto.size(), response.size());
  }
}
