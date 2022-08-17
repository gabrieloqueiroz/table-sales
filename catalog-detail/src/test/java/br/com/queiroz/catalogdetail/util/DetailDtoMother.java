package br.com.queiroz.catalogdetail.util;

import br.com.queiroz.catalogdetail.dto.DetailDto;

public class DetailDtoMother {

  public static DetailDto getDetailDto() {
    DetailDto detailDto = new DetailDto();

    detailDto.setColor("default");
    detailDto.setDescription("testestestesteste");
    detailDto.setManual("xxxxxxxx");
    detailDto.setId(1L);

    return detailDto;
  }
}
