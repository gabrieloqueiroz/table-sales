package br.com.queiroz.catalogconsumer.controller;

import br.com.queiroz.catalogconsumer.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {

  private ConsumerService consumerService;

  @Autowired
  public ConsumerController(ConsumerService consumerService){
    this.consumerService = consumerService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<FullDetailDto> getProductWithPrice(@PathVariable Long id){
    FullDetailDto fullDetail = consumerService.getFullDetail(id);

    return ResponseEntity.ok(fullDetail);
  }
}
