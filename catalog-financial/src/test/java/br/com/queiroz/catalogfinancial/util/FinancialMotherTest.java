package br.com.queiroz.catalogfinancial.util;

import br.com.queiroz.catalogfinancial.dto.FinancialDto;
import br.com.queiroz.catalogfinancial.model.Financial;

import java.math.BigDecimal;
import java.util.List;

public class FinancialMotherTest {

  public static FinancialDto getFinancialDto(){
    FinancialDto financialDto = new FinancialDto();

    financialDto.setId(1L);
    financialDto.setSalePrice(new BigDecimal(1000));
    financialDto.setPurchasePrice(new BigDecimal(600));

    return financialDto;
  }

  public static Financial getFinancial(){
    return new Financial(1L, new BigDecimal(1000), new BigDecimal(600));
  }

  public static List<Financial> getListOfFinancial(){
    return List.of(
        new Financial(1L, new BigDecimal(1000), new BigDecimal(500)),
        new Financial(2L, new BigDecimal(1100), new BigDecimal(600)),
        new Financial(3L, new BigDecimal(1300), new BigDecimal(800))
    );
  }
}
