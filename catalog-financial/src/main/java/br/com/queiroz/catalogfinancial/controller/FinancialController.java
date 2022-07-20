package br.com.queiroz.catalogfinancial.controller;

import br.com.queiroz.catalogfinancial.dto.FinancialDto;
import br.com.queiroz.catalogfinancial.services.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/financial")
public class FinancialController {

  FinancialService financialService;

  @Autowired
  public FinancialController(FinancialService financialService){
    this.financialService = financialService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<FinancialDto> getFinancialById(@PathVariable Long id) throws InterruptedException {
    FinancialDto financial = financialService.getFinancialById(id);

    Thread.sleep(3000);
    return ResponseEntity.ok(financial);
  }

  @PostMapping
  public ResponseEntity<FinancialDto> createFinancial(@RequestBody FinancialDto financialDto, UriComponentsBuilder builder){
    FinancialDto createdFinancial = financialService.createFinancial(financialDto);

    URI uri = builder.path("/financial/{id}").buildAndExpand(createdFinancial.getId()).toUri();

    return ResponseEntity.created(uri).body(createdFinancial);
  }
}
