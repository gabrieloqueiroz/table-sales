package br.com.queiroz.catalogfinancial.services;

import br.com.queiroz.catalogfinancial.dto.FinancialDto;
import br.com.queiroz.catalogfinancial.model.Financial;
import br.com.queiroz.catalogfinancial.repository.FinancialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialService {

  private FinancialRepository financialRepository;
  private ModelMapper modelMapper;

  @Autowired
  public FinancialService(FinancialRepository financialRepository, ModelMapper modelMapper) {
    this.financialRepository = financialRepository;
    this.modelMapper = modelMapper;
  }

  public FinancialDto getFinancialById(Long id) {
    Financial financial = financialRepository.findById(id)
        .orElseThrow(EntityNotFoundException::new);

    return modelMapper.map(financial, FinancialDto.class);
  }

  public FinancialDto createFinancial(FinancialDto financialDto) {
    Financial financial = modelMapper.map(financialDto, Financial.class);
    Financial financialSaved = financialRepository.save(financial);

    return modelMapper.map(financialSaved, FinancialDto.class);
  }

  public List<FinancialDto> getFinancialByRangePrice(BigDecimal min, BigDecimal max) {
    List<Financial> byRangePrice = financialRepository.getByRangePrice(min, max);

    List<FinancialDto> FinancialDto = byRangePrice.stream().map(financial -> modelMapper.map(financial, FinancialDto.class)).toList();

    return FinancialDto;
  }
}
