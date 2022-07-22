package br.com.queiroz.catalogdetail.repository;

import br.com.queiroz.catalogdetail.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {

  @Query(value = "SELECT * FROM detail d "
      + "WHERE d.id IN (:ids)", nativeQuery = true)
  List<Detail> findByIds(
      @Param("ids") Set<Long> ids
  );

}
