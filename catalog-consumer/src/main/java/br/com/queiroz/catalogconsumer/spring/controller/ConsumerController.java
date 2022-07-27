package br.com.queiroz.catalogconsumer.spring.controller;

import br.com.queiroz.catalogconsumer.spring.dto.FullDetailDto;
import br.com.queiroz.catalogconsumer.spring.service.ConsumerService;
import br.om.queiroz.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_CONSUMER_SERVICE)
public class ConsumerController {

  private ConsumerService consumerService;

  @Autowired
  public ConsumerController(ConsumerService consumerService){
    this.consumerService = consumerService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<FullDetailDto> getProductWithPrice(@PathVariable Long id){
    try{
      FullDetailDto fullDetail = consumerService.getFullDetail(id);
      return ResponseEntity.ok(fullDetail);
    }catch (Exception e){
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_RANGE)
  public ResponseEntity<List<FullDetailDto>> getFinancialByRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max ){
    try {
      List<FullDetailDto> fullDetail = consumerService.getFinancialByRange(min, max);
      return ResponseEntity.ok(fullDetail);
    }catch (Exception e){
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_CREATE)
  public ResponseEntity<FullDetailDto>  createProduct(@RequestBody FullDetailDto request, UriComponentsBuilder uriCB){
    try {
      FullDetailDto fullDetailDto = consumerService.createProduct(request);
      URI uri = uriCB.path(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_CONSUMER_SERVICE + ConstantsUtils.PATH_SEPARATOR + "{id}").buildAndExpand(fullDetailDto.getId()).toUri();
      return ResponseEntity.created(uri).body(fullDetailDto);
    }catch (Exception e){
      return ResponseEntity.badRequest().build();
    }
  }
}
