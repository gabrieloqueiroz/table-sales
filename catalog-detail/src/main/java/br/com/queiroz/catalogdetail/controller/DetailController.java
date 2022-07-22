package br.com.queiroz.catalogdetail.controller;

import br.com.queiroz.catalogdetail.dto.DetailDto;
import br.com.queiroz.catalogdetail.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/detail")
public class DetailController {

  private final DetailService detailService;

  @Autowired
  public DetailController(DetailService detailService) {
    this.detailService = detailService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetailDto> findById(@PathVariable Long id) throws InterruptedException {
    DetailDto product = detailService.findById(id);

    return ResponseEntity.ok(product);
  }

  @PostMapping
  public ResponseEntity<DetailDto> createProduct(@RequestBody DetailDto request, UriComponentsBuilder uriBuilder) {
    DetailDto product = detailService.createProduct(request);

    URI uri = uriBuilder.path("/detail/{id}").buildAndExpand(product.getId()).toUri();

    return ResponseEntity.created(uri).body(product);
  }

  @PostMapping("/listid")
  public ResponseEntity<List<DetailDto>> findByIds(@RequestBody Set<Long> ids){
    List<DetailDto> byIds = detailService.findByIds(ids);
    return ResponseEntity.ok(byIds);
  }
}
