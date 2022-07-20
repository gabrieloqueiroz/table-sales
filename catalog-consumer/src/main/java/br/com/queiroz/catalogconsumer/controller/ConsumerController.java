package br.com.queiroz.catalogconsumer.controller;

import br.com.queiroz.catalogconsumer.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

  @PostMapping("/create")
  public ResponseEntity<FullDetailDto>  createProduct(@RequestBody FullDetailDto request, UriComponentsBuilder uriCB){
    FullDetailDto fullDetailDto = consumerService.createProduct(request);

    URI uri = uriCB.path("/consumer/{id}").buildAndExpand(fullDetailDto.getId()).toUri();

    return ResponseEntity.created(uri).body(fullDetailDto);

  }
}
