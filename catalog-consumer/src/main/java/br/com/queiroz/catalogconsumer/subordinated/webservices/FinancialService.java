package br.com.queiroz.catalogconsumer.subordinated.webservices;

import br.com.queiroz.catalogconsumer.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.subordinated.webservices.config.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FinancialService {

  private ClientConfiguration clientConfiguration;
  private WebClient webClient;

  @Autowired
  public FinancialService(ClientConfiguration clientConfiguration, WebClient webClient) {
    this.clientConfiguration = clientConfiguration;
    this.webClient = webClient;
  }

  public FullDetailDto getFinancialProduct(Long id){
    return this.webClient
        .get()
        .uri(clientConfiguration.getFinancialUrl() + "/{id}", id)
        .retrieve()
        .bodyToMono(FullDetailDto.class)
        .block();
  }
}
