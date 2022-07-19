package br.com.queiroz.catalogfinancial.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinancialDto {
  private Long id;
  private BigDecimal salePrice;
  private BigDecimal purchasePrice;
}
