package br.com.queiroz.catalogdetail.repository;

import br.com.queiroz.catalogdetail.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {
}
