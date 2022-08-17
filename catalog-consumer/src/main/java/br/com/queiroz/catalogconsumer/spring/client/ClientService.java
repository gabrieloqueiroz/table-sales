package br.com.queiroz.catalogconsumer.spring.client;

import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.subordinated.DetailService;
import br.com.queiroz.catalogconsumer.spring.subordinated.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientService {

  private DetailService detailService;
  private FinancialService financialService;

  @Autowired
  public ClientService(DetailService detailService, FinancialService financialService) {
    this.detailService = detailService;
    this.financialService = financialService;
  }

  public FullDetailDto getProductWithPrice(Long id) {
    Mono<FullDetailDto> fullDetailDtoMono = detailService.findById(id);
    Mono<FullDetailDto> fullFinancialDtoMono = financialService.financialById(id);

    FullDetailDto detailFull = Mono.zip(fullDetailDtoMono, fullFinancialDtoMono).map(p -> {
      p.getT1().setSalePrice(p.getT2().getSalePrice());
      return p.getT1();
    }).block();

    return detailFull;
  }

  public FullDetailDto createProductWithPrice(FullDetailDto request) {
    Mono<FullDetailDto> fullDetailDtoMono = detailService.createProduct(request);
    Mono<FullDetailDto> fullFinancialDtoMono = financialService.createProduct(request);

    FullDetailDto fullProduct = Mono.zip(fullDetailDtoMono, fullFinancialDtoMono).map(p -> {
      p.getT1().setSalePrice(p.getT2().getSalePrice());
      p.getT1().setPurchasePrice(p.getT2().getPurchasePrice());
      return p.getT1();
    }).block();

    return fullProduct;
  }

  public List<FullDetailDto> getFinancialByRange(BigDecimal min, BigDecimal max) {
    Flux<FullDetailDto> fullDetailDtoFlux = financialService.findByListIds(min, max);
    return fullDetailDtoFlux.collectList().block();
  }

  public List<FullDetailDto> getDetailById(List<FullDetailDto> financialByRange) {
    Set<Long> ids = financialByRange.stream().map(FullDetailDto::getId).collect(Collectors.toSet());
    Flux<FullDetailDto> fullDetailDtoFlux = detailService.findByListIds(ids);
    return fullDetailDtoFlux.collectList().block();
  }
}
