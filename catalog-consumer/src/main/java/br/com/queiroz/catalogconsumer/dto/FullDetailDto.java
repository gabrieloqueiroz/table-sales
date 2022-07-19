package br.com.queiroz.catalogconsumer.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FullDetailDto {
  private Long id;
  private String description;
  private String color;
  private BigDecimal salePrice;
}
