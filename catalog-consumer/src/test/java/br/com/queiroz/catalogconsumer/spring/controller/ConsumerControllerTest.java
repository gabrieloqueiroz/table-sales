package br.com.queiroz.catalogconsumer.spring.controller;

import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.service.ConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

import static br.com.queiroz.catalogconsumer.spring.util.FullDetailMother.getFullDetailDtoMother;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsumerControllerTest {

  private ConsumerController consumerController;
  private ConsumerService consumerService;

  @BeforeEach
  public void setup(){
    this.consumerService = mock(ConsumerService.class);
    this.consumerController = new ConsumerController(this.consumerService);
  }

  @Test
  public void should_return_product_with_price_get_by_id(){
    //Given
    FullDetailDto expectedFullDto = getFullDetailDtoMother().get(0);
    Long requestId = 1L;

    when(consumerService.getFullDetail(any())).thenReturn(expectedFullDto);

    //When
    ResponseEntity<FullDetailDto> response = consumerController.getProductWithPrice(requestId);

    //Then
    assertNotNull(response);
    assertEquals(expectedFullDto, response.getBody());
    assertEquals(HttpStatus.OK,response.getStatusCode());
  }

  @Test
  public void should_throw_when_return_error_get_by_id(){
    //Given
    Long requestId = 1L;
    when(consumerService.getFullDetail(any())).thenThrow(EntityNotFoundException.class);

    //When
    ResponseEntity<FullDetailDto> response = consumerController.getProductWithPrice(requestId);

    //Then
    assertNotNull(response);
    assertNull(response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void should_return_product_by_range_price(){
    //Given
    BigDecimal min = new BigDecimal(60);
    BigDecimal max = new BigDecimal(90);
    List<FullDetailDto> expectedDetailDto = getFullDetailDtoMother();

    when(consumerService.getFinancialByRange(any(), any())).thenReturn(expectedDetailDto);

    //When
    ResponseEntity<List<FullDetailDto>> response = consumerController.getFinancialByRange(min, max);

    //Then
    assertNotNull(response);
    assertEquals(expectedDetailDto, response.getBody());
  }

  @Test
  public void should_return_throw_when_error_in_get_by_range(){
    // Given
    BigDecimal min = new BigDecimal(60);
    BigDecimal max = new BigDecimal(90);

    when(consumerService.getFinancialByRange(any(), any())).thenThrow(EntityNotFoundException.class);

    // When
    ResponseEntity<List<FullDetailDto>> response = consumerController.getFinancialByRange(min, max);

    // Then
    assertNotNull(response);
    assertNull(response.getBody());
    assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
  }

  @Test
  public void should_create_new_product_detail_with_price(){
    //Given
    FullDetailDto expectedDetail = getFullDetailDtoMother().stream().findFirst().orElse(null);

    when(consumerService.createProduct(any())).thenReturn(expectedDetail);

    //When
    ResponseEntity<FullDetailDto> response = consumerController.createProduct(expectedDetail, any());

    //Then
    assertNotNull(response);
  }
}
