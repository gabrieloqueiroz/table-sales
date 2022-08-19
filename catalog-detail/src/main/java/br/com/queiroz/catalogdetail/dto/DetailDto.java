package br.com.queiroz.catalogdetail.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DetailDto {
  private Long id;
  private String description;
  private String manual;
  private String color;

  @Override
  public String toString() {
    return id + " " + description + " " + manual + " " + color;
  }
}
