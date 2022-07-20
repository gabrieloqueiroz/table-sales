package br.com.queiroz.catalogfinancial.repository;

import br.com.queiroz.catalogfinancial.model.Financial;
import br.com.queiroz.catalogfinancial.application.FinancialServiceApplicationTestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { FinancialServiceApplicationTestConfiguration.class })
public class FinancialRepositoryTest {

  private FinancialRepository financialRepository;

  @Autowired
  public FinancialRepositoryTest(FinancialRepository financialRepository){
    this.financialRepository = financialRepository;
  }

  @Test
  @Transactional
  public void should_return_by_range(){
    //Given
    BigDecimal minExpected = new BigDecimal(65);
    BigDecimal maxExpected = new BigDecimal(92);

    List<Financial> byRangePrice = financialRepository.getByRangePrice(minExpected, maxExpected);

    Assertions.assertNotNull(byRangePrice);
  }

}
