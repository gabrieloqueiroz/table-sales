package br.com.queiroz.catalogconsumer.client;

import br.com.queiroz.catalogconsumer.dto.FullDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

  private WebClient webClientDetail;
  private WebClient webClientFinancial;

  @Autowired
  public ClientService(WebClient webClientDetail, WebClient webClientFinancial) {
    this.webClientDetail = webClientDetail;
    this.webClientFinancial = webClientFinancial;
  }

  public FullDetailDto getProductWithPrice(Long id) {
    Mono<FullDetailDto> fullDetailDtoMono = webClientDetail
        .get()
        .uri("/{id}", id)
        .retrieve()
        .bodyToMono(FullDetailDto.class);

    Mono<FullDetailDto> fullFinancialDtoMono = webClientFinancial
        .get()
        .uri("/{id}", id)
        .retrieve()
        .bodyToMono(FullDetailDto.class);

    FullDetailDto detailFull = Mono.zip(fullDetailDtoMono, fullFinancialDtoMono).map(p -> {
      p.getT1().setSalePrice(p.getT2().getSalePrice());
      return p.getT1();
    }).block();

    return detailFull;
  }

  public FullDetailDto createProductWithPrice(FullDetailDto request) {
    Mono<FullDetailDto> fullDetailDtoMono = webClientDetail
        .post()
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .bodyToMono(FullDetailDto.class);

    Mono<FullDetailDto> fullFinancialDtoMono = webClientFinancial
        .post()
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .bodyToMono(FullDetailDto.class);

    FullDetailDto fullProduct = Mono.zip(fullDetailDtoMono, fullFinancialDtoMono).map(p -> {
      p.getT1().setSalePrice(p.getT2().getSalePrice());
      p.getT1().setPurchasePrice(p.getT2().getPurchasePrice());
      return p.getT1();
    }).block();

    return fullProduct;
  }

  public List<FullDetailDto> getFinancialByRange(BigDecimal min, BigDecimal max) {
    Flux<FullDetailDto> fullDetailDtoFlux =
        webClientFinancial
            .get()
            .uri("/range?min={min}&max={max}", min, max)
            .retrieve()
            .bodyToFlux(FullDetailDto.class);

    return fullDetailDtoFlux.collectList().block();
  }

  public List<FullDetailDto> getDetailById(List<FullDetailDto> financialByRange) {

    List<Long> ids = financialByRange.stream().map(FullDetailDto::getId).toList();

    Flux<FullDetailDto> fullDetailDtoFlux =
      webClientDetail
          .post()
          .uri("/listid")
          .body(BodyInserters.fromValue(ids))
          .retrieve()
          .bodyToFlux(FullDetailDto.class);

    return fullDetailDtoFlux.collectList().block();
  }
}
