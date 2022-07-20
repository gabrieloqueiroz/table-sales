package br.com.queiroz.catalogconsumer.client;

import br.com.queiroz.catalogconsumer.dto.FullDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
}
