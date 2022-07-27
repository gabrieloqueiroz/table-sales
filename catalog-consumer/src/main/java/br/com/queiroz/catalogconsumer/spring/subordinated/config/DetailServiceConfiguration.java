package br.com.queiroz.catalogconsumer.spring.subordinated.config;

import br.om.queiroz.utils.ConstantsUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@Data
@NoArgsConstructor
public class DetailServiceConfiguration {

  @Value("${detail.service.host}")
  private String serviceHost;

  private String serviceName = ConstantsUtils.PATH_DETAIL_SERVICE;

  public String getUrl(){
    return this.getServiceHost() + ConstantsUtils.PATH_SEPARATOR + this.getServiceName();
  }

  public String getUrlFindById(Long id){
      return getUrl() + ConstantsUtils.PATH_SEPARATOR + id;
  }

  public String getUrlCreateProduct(){
    return getUrl();
  }

  public String getUrlFindByListId(){
    return getUrl() + ConstantsUtils.PATH_SEPARATOR + ConstantsUtils.PATH_DETAIL_LIST_IDS;
  }
}
