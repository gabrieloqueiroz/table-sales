package br.com.queiroz.catalogconsumer.spring.subordinated;

import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.subordinated.config.DetailServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class DetailService {

  private DetailServiceConfiguration detailServiceConfiguration;
  private WebClient webClient;

  @Autowired
  public DetailService(DetailServiceConfiguration detailServiceConfiguration, WebClient webClient) {
    this.detailServiceConfiguration = detailServiceConfiguration;
    this.webClient = webClient;
  }

  public Mono<FullDetailDto> findById(Long id){
    return webClient
        .get()
        .uri(detailServiceConfiguration.getUrlFindById(id))
        .retrieve()
        .bodyToMono(FullDetailDto.class);
  }

  public Mono<FullDetailDto> createProduct(FullDetailDto request){
    return webClient
        .post()
        .uri(detailServiceConfiguration.getUrlCreateProduct())
        .body(BodyInserters.fromValue(request))
        .retrieve()
        .bodyToMono(FullDetailDto.class);
  }

  public Flux<FullDetailDto> findByListIds(Set<Long> ids){
    return webClient
        .post()
        .uri(detailServiceConfiguration.getUrlFindByListId())
        .body(BodyInserters.fromValue(ids))
        .retrieve()
        .bodyToFlux(FullDetailDto.class);
  }
}
