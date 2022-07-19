package br.com.queiroz.catalogdetail.service;


import br.com.queiroz.catalogdetail.dto.DetailDto;
import br.com.queiroz.catalogdetail.model.Detail;
import br.com.queiroz.catalogdetail.repository.DetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DetailService {

  private DetailRepository detailRepository;
  private ModelMapper modelMapper;

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
}
