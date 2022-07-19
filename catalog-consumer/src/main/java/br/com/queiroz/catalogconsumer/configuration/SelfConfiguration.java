package br.com.queiroz.catalogconsumer.configuration;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class SelfConfiguration {

  @Bean
  public WebClient webClient() {
    HttpClient option = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 40000);
    return WebClient.builder().clientConnector(new ReactorClientHttpConnector(option))
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
  }
}
