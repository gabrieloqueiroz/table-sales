package br.com.queiroz.catalogfinancial.repository;

import br.com.queiroz.catalogfinancial.model.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FinancialRepository extends JpaRepository<Financial, Long> {

  @Query(value = "SELECT * FROM financial fin "
      + "WHERE fin.sale_price >= :min  "
      + "AND fin.sale_price <= :max", nativeQuery = true )
  List<Financial> getByRangePrice(
      @Param("min") BigDecimal min,
      @Param("max") BigDecimal max
  );

}
