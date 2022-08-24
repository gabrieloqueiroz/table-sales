package br.com.queiroz.catalogfinancial.repository;

import br.com.queiroz.catalogfinancial.config.SelfConfiguration;
import br.com.queiroz.catalogfinancial.model.Financial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@ComponentScan("br.com.queiroz.catalogfinancial.*")
@EntityScan("br.com.queiroz.catalogfinancial.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { SelfConfiguration.class })
public class FinancialRepositoryTest {

  private FinancialRepository financialRepository;

  @Autowired
  public FinancialRepositoryTest(FinancialRepository financialRepository){
    this.financialRepository = financialRepository;
  }

  @Test
  public void should_return_by_range(){
    //Given
    BigDecimal minExpected = new BigDecimal(65);
    BigDecimal maxExpected = new BigDecimal(92);

    //when
    List<Financial> byRangePrice = financialRepository.getByRangePrice(minExpected, maxExpected);

    //then
    Assertions.assertNotNull(byRangePrice);
  }
}
