package br.com.queiroz.catalogdetail.service;

import br.com.queiroz.catalogdetail.dto.DetailDto;
import br.com.queiroz.catalogdetail.model.Detail;
import br.com.queiroz.catalogdetail.repository.DetailRepository;
import br.com.queiroz.catalogdetail.util.DetailDtoMother;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DetailServiceTest {

  private DetailService detailService;
  private DetailRepository detailRepository;

  @BeforeEach
  public void setup() {
    detailRepository = mock(DetailRepository.class);
    detailService = new DetailService(detailRepository, new ModelMapper());
  }

  @Test
  public void search_detail_by_id() {
    //Given
    Long expectedId = 1L;
    Optional<Detail> detailDto = Optional.of(DetailDtoMother.getDetail());

    when(detailRepository.findById(any())).thenReturn(detailDto);

    //When
    DetailDto response = detailService.findById(expectedId);

    //Then
    assertNotNull(response);
    assertEquals(detailDto.get().getId(), response.getId());
    assertEquals(detailDto.get().getDescription(), response.getDescription());
    assertEquals(detailDto.get().getManual(), response.getManual());
    assertEquals(detailDto.get().getColor(), response.getColor());
  }

  @Test
  public void search_by_list_ids() {
    //Given
    Set<Long> request = Set.of(1L, 2L, 3L);
    List<Detail> listDetail = DetailDtoMother.getListDetail();
    when(detailRepository.findByIds(any())).thenReturn(listDetail);

    //When
    List<DetailDto> response = detailService.findByIds(request);

    //Then
    assertNotNull(response);
    listDetail.forEach(detail -> {
      Optional<DetailDto> first = response.stream().filter(resp -> resp.getId().equals(detail.getId())).findFirst();

      assertTrue(first.isPresent());
      assertEquals(detail.getColor(), first.get().getColor());
      assertEquals(detail.getManual(), first.get().getManual());
      assertEquals(detail.getDescription(), first.get().getDescription());
    });
  }

  @Test
  public void should_return_created_detail(){
    //Given
    DetailDto request = DetailDtoMother.getDetailDto();
    Detail returnRepository = DetailDtoMother.getDetail();
    request.setId(null);
    when(detailRepository.save(any())).thenReturn(returnRepository);

    //When
    DetailDto response = detailService.createProduct(request);

    //Then
    assertNotNull(response);
  }
}
