package br.com.queiroz.catalogfinancial.controller;

import br.com.queiroz.catalogfinancial.dto.FinancialDto;
import br.com.queiroz.catalogfinancial.services.FinancialService;
import br.om.queiroz.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_FINANCIAL_SERVICE)
public class FinancialController {

  private final FinancialService financialService;

  @Autowired
  public FinancialController(FinancialService financialService){
    this.financialService = financialService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<FinancialDto> getFinancialById(@PathVariable Long id) throws InterruptedException {
    FinancialDto financial = financialService.getFinancialById(id);

    return ResponseEntity.ok(financial);
  }
    @GetMapping(ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_FINANCIAL_BY_RANGE)
  public ResponseEntity<List<FinancialDto>> getFinancialByRangeAmount(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
    List<FinancialDto> financialByRangePrice = financialService.getFinancialByRangePrice(min, max);

    return ResponseEntity.ok(financialByRangePrice);
  }

  @PostMapping
  public ResponseEntity<FinancialDto> createFinancial(@RequestBody FinancialDto financialDto, UriComponentsBuilder builder){
    FinancialDto createdFinancial = financialService.createFinancial(financialDto);

    URI uri = builder.path("/financial/{id}").buildAndExpand(createdFinancial.getId()).toUri();

    return ResponseEntity.created(uri).body(createdFinancial);
  }
}
