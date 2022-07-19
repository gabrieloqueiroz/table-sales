package br.com.queiroz.catalogconsumer.service;

import br.com.queiroz.catalogconsumer.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.subordinated.webservices.DetailService;
import br.com.queiroz.catalogconsumer.subordinated.webservices.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

  private DetailService detailService;
  private FinancialService financialService;

  @Autowired
  public ConsumerService(DetailService detailService, FinancialService financialService){
    this.detailService = detailService;
    this.financialService = financialService;
  }

  public FullDetailDto getFullDetail(Long id) {
    FullDetailDto detailProduct = detailService.getDetailProduct(id);
    FullDetailDto financialProduct = financialService.getFinancialProduct(id);

    detailProduct.setSalePrice(financialProduct.getSalePrice());

    return detailProduct;
  }
}
