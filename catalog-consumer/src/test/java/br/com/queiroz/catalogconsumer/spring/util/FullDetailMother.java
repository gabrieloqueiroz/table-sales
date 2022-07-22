package br.com.queiroz.catalogconsumer.spring.util;

import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FullDetailMother {

  public static List<FullDetailDto> getFullDetailDtoMother(){
   return new ArrayList<>(List.of(
        new FullDetailDto(
            1L,
            "test for unit",
            "xxxxxx",
            "default",
            new BigDecimal(100),
            new BigDecimal(45)
            ),
        new FullDetailDto(
            1L,
            "test for unit",
            "xxxxxx",
            "default",
            new BigDecimal(140),
            new BigDecimal(70)
        ),
        new FullDetailDto(
            1L,
            "test for unit",
            "xxxxxx",
            "default",
            new BigDecimal(192),
            new BigDecimal(85)
        )
    ));
  }

  public static FullDetailDto getDetailDto(){
    FullDetailDto dtoMother = new FullDetailDto();

    dtoMother.setId(1L);
    dtoMother.setColor("default");
    dtoMother.setDescription("test for unit");
    dtoMother.setManual("xxxxxx");
    dtoMother.setSalePrice(null);
    dtoMother.setPurchasePrice(null);

    return dtoMother;
  }

  public static FullDetailDto getFinancialDto(){
    FullDetailDto dtoMother = new FullDetailDto();

    dtoMother.setId(1L);
    dtoMother.setColor(null);
    dtoMother.setDescription(null);
    dtoMother.setManual(null);
    dtoMother.setSalePrice(new BigDecimal(100));
    dtoMother.setPurchasePrice(new BigDecimal(45));

    return dtoMother;
  }
}
