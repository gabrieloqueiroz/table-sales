package br.com.queiroz.catalogconsumer.service;

import br.com.queiroz.catalogconsumer.client.ClientService;
import br.com.queiroz.catalogconsumer.dto.FullDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    return clientService.getFinancialByRange(min, max);
  }
}
