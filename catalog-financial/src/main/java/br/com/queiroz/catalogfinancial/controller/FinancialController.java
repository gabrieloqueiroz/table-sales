package br.com.queiroz.catalogfinancial.controller;

import br.com.queiroz.catalogfinancial.dto.FinancialDto;
import br.com.queiroz.catalogfinancial.services.FinancialService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/financial")
public class FinancialController {

  FinancialService financialService;

  @Autowired
  public FinancialController(FinancialService financialService){
    this.financialService = financialService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<FinancialDto> getFinancialById(@PathVariable Long id){
    FinancialDto financial = financialService.getFinancialById(id);
    return ResponseEntity.ok(financial);
  }
}
