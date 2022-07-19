package br.com.queiroz.catalogfinancial.repository;

import br.com.queiroz.catalogfinancial.model.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialRepository extends JpaRepository<Financial, Long> {
}
