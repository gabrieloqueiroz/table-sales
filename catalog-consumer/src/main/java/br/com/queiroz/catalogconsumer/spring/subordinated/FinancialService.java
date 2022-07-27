package br.com.queiroz.catalogconsumer.spring.subordinated;

import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.subordinated.config.DetailServiceConfiguration;
import br.com.queiroz.catalogconsumer.spring.subordinated.config.FinancialServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class FinancialService {

  private FinancialServiceConfiguration financialServiceConfiguration;
  private WebClient webClient;

  @Autowired
  public FinancialService(FinancialServiceConfiguration financialServiceConfiguration, WebClient webClient) {
    this.financialServiceConfiguration = financialServiceConfiguration;
    this.webClient = webClient;
  }

  public Mono<FullDetailDto> financialById(Long id){
    return webClient
        .get()
        .uri(financialServiceConfiguration.getUrlFinancialById(id))
        .retrieve()
        .bodyToMono(FullDetailDto.class);
  }

  public Mono<FullDetailDto> createProduct(FullDetailDto request){
    return webClient
        .post()
        .uri(financialServiceConfiguration.getUrlCreateFinancial())
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .bodyToMono(FullDetailDto.class);
  }

  public Flux<FullDetailDto> findByListIds(BigDecimal min, BigDecimal max){
    return webClient
        .get()
        .uri(financialServiceConfiguration.getUrlFinancialByListId(min, max))
        .retrieve()
        .bodyToFlux(FullDetailDto.class);
  }
}
