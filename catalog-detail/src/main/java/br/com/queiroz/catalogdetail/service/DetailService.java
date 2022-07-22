package br.com.queiroz.catalogdetail.service;


import br.com.queiroz.catalogdetail.dto.DetailDto;
import br.com.queiroz.catalogdetail.model.Detail;
import br.com.queiroz.catalogdetail.repository.DetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DetailService {

  private final DetailRepository detailRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public DetailService(DetailRepository detailRepository, ModelMapper modelMapper) {
    this.detailRepository = detailRepository;
    this.modelMapper = modelMapper;
  }

  public DetailDto findById(Long id) {
    Detail product = detailRepository.findById(id)
        .orElseThrow(EntityNotFoundException::new);

    return modelMapper.map(product, DetailDto.class);
  }

  public DetailDto createProduct(DetailDto request) {
    Detail register = modelMapper.map(request, Detail.class);
    Detail saved = detailRepository.save(register);

    return modelMapper.map(saved, DetailDto.class);
  }

  public List<DetailDto> findByIds(Set<Long> ids) {
    List<Detail> details = detailRepository.findByIds(ids);
    List<DetailDto> detailsDto = details.stream().map(dtl -> modelMapper.map(dtl, DetailDto.class)).toList();
    return detailsDto;
  }
}
