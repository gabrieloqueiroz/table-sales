package br.com.queiroz.catalogdetail.repository;

import br.com.queiroz.catalogdetail.config.SelfConfiguration;
import br.com.queiroz.catalogdetail.model.Detail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@ComponentScan("br.com.queiroz.catalogdetail.*")
@EntityScan("br.com.queiroz.catalogdetail.*")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { SelfConfiguration.class })
public class DetailRepositoryTest {

  DetailRepository detailRepository;

  @Autowired
  public DetailRepositoryTest(DetailRepository detailRepository) {
    this.detailRepository = detailRepository;
  }

  @Test
  public void should_return_find_detail_by_range(){
    //Given
    Set<Long> request = new HashSet<>();
    request.add(1L);
    request.add(2L);
    request.add(3L);

    //When
    List<Detail> response = detailRepository.findByIds(request);

    //Then
    assertNotNull(response);
  }
}
