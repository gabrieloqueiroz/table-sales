package br.com.queiroz.catalogfinancial.service;

import br.com.queiroz.catalogfinancial.dto.FinancialDto;
import br.com.queiroz.catalogfinancial.model.Financial;
import br.com.queiroz.catalogfinancial.repository.FinancialRepository;
import br.com.queiroz.catalogfinancial.services.FinancialService;
import br.com.queiroz.catalogfinancial.util.FinancialMotherTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FinancialServiceTest {

  private FinancialService financialService;
  private FinancialRepository financialRepository;

  @BeforeEach
  private void setup() {
    financialRepository = mock(FinancialRepository.class);
    financialService = new FinancialService(financialRepository, new ModelMapper());
  }

  @Test
  public void should_return_financial_by_id() {
    //Given
    Long requestId = 1L;
    Optional<Financial> financial = Optional.of(FinancialMotherTest.getFinancial());

    when(financialRepository.findById(any())).thenReturn(financial);

    //When
    FinancialDto response = financialService.getFinancialById(requestId);

    //Then
    assertNotNull(response);
    assertEquals(financial.get().getId(), response.getId());
    assertEquals(financial.get().getSalePrice(), response.getSalePrice());
    assertEquals(financial.get().getPurchasePrice(), response.getPurchasePrice());
  }

  @Test
  public void should_return_financials_by_range() {
    //Given
    BigDecimal minRequest = new BigDecimal(990);
    BigDecimal maxRequest = new BigDecimal(1400);
    List<Financial> expectedListFinancial = FinancialMotherTest.getListOfFinancial();
    when(financialRepository.getByRangePrice(any(), any())).thenReturn(expectedListFinancial);

    //When
    List<FinancialDto> response = financialService.getFinancialByRangePrice(minRequest, maxRequest);

    //Then
    assertNotNull(response);
    expectedListFinancial.forEach(financial -> {
      Optional<FinancialDto> register = response.stream().filter(resp -> resp.getId().equals(financial.getId())).findFirst();

      assertTrue(register.isPresent());
      assertEquals(financial.getId(), register.get().getId());
      assertEquals(financial.getPurchasePrice(), register.get().getPurchasePrice());
      assertEquals(financial.getSalePrice(), register.get().getSalePrice());
    });

  }

  @Test
  public void should_create_financial(){
    //Given
    FinancialDto request = FinancialMotherTest.getFinancialDto();
    Financial expectedResponse = FinancialMotherTest.getFinancial();

    when(financialRepository.save(any())).thenReturn(expectedResponse);

    //When
    FinancialDto response = financialService.createFinancial(request);

    //Then
    assertNotNull(response);
    assertEquals(expectedResponse.getId(), response.getId());
    assertEquals(expectedResponse.getPurchasePrice(), response.getPurchasePrice());
    assertEquals(expectedResponse.getSalePrice(), response.getSalePrice());
  }
}
