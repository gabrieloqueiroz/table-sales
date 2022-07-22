package br.com.queiroz.catalogconsumer.spring.service;

import br.com.queiroz.catalogconsumer.spring.client.ClientService;
import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ConsumerService {

  ClientService clientService;

  @Autowired
  public ConsumerService(ClientService clientService) {
    this.clientService = clientService;
  }

  public FullDetailDto getFullDetail(Long id) {
    return clientService.getProductWithPrice(id);
  }

  public FullDetailDto createProduct(FullDetailDto request) {
    return clientService.createProductWithPrice(request);
  }

  public List<FullDetailDto> getFinancialByRange(BigDecimal min, BigDecimal max) {
    List<FullDetailDto> financialByRange = clientService.getFinancialByRange(min, max);
    List<FullDetailDto> detailByIds = clientService.getDetailById(financialByRange);

    financialByRange.forEach(financial -> {
      FullDetailDto detailDto = detailByIds.stream().filter(detail ->
              detail.getId()
                  .equals(financial.getId()))
                  .findFirst()
          .orElseThrow(EntityNotFoundException::new);

      financial.setColor(detailDto.getColor());
      financial.setDescription(detailDto.getDescription());
      financial.setManual(detailDto.getManual());
    });

    return financialByRange;
  }
}
