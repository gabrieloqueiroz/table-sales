package br.com.queiroz.catalogconsumer.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullDetailDto {
  private Long id;
  private String description;
  private String manual;
  private String color;
  private BigDecimal salePrice;
  private BigDecimal purchasePrice;
}
