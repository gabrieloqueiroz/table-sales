package br.com.queiroz.catalogconsumer.spring.subordinated.config;

import br.om.queiroz.utils.ConstantsUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@Data
@NoArgsConstructor
public class FinancialServiceConfiguration {

  @Value("${financial.service.host}")
  private String serviceHost;

  private String serviceName = ConstantsUtils.PATH_FINANCIAL_SERVICE;

  public String getUrl(){
    return this.getServiceHost() + ConstantsUtils.PATH_SEPARATOR + this.getServiceName();
  }

  public String getUrlFinancialById(Long id){
      return getUrl() + ConstantsUtils.PATH_SEPARATOR + id;
  }

  public String getUrlCreateFinancial(){
    return getUrl();
  }

  public String getUrlFinancialByListId(BigDecimal min, BigDecimal max){
    return getUrl() + ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_FINANCIAL_BY_RANGE + "?min=" + min +"&max=" + max;
  }
}
