package br.com.queiroz.catalogconsumer.spring.configuration;

import br.om.queiroz.utils.ConstantsUtils;
import io.netty.channel.ChannelOption;
import lombok.Data;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
@Data
@Configuration
public class SelfConfiguration {

  @Value("${consumer.service.host}")
  private String serviceHost;

  private String serviceName = ConstantsUtils.PATH_CONSUMER_SERVICE;

  public String getUrl(){
    return this.getServiceHost() + ConstantsUtils.PATH_SEPARATOR + this.getServiceName();
  }

  @Bean
  @Primary
  public WebClient webClient() {
    HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 40000);
    return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
  }
}
