package br.com.queiroz.catalogdetail.util;

import br.com.queiroz.catalogdetail.dto.DetailDto;
import br.com.queiroz.catalogdetail.model.Detail;

import java.util.List;

public class DetailDtoMother {

  public static DetailDto getDetailDto() {
    DetailDto detailDto = new DetailDto();

    detailDto.setColor("default");
    detailDto.setDescription("testestestesteste");
    detailDto.setManual("xxxxxxxx");
    detailDto.setId(1L);

    return detailDto;
  }

  public static Detail getDetail() {
    Detail detail = new Detail();

    detail.setColor("default");
    detail.setDescription("testestestesteste");
    detail.setManual("xxxxxxxx");
    detail.setId(1L);

    return detail;
  }

  public static List<Detail> getListDetail(){
    return List.of(
        new Detail(1L, "Teste1", "xxxxx", "default"),
        new Detail(2L, "Teste2", "xxxxx", "azul"),
        new Detail(3L, "Teste3", "xxxxx", "amarelo")
    );
  }
}
