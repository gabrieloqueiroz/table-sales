package br.com.queiroz.catalogdetail.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailDto {
  private Long id;
  private String description;
  private String manual;
  private String color;
}
