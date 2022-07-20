package br.com.queiroz.catalogfinancial.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@ComponentScan("br.com.queiroz.catalogfinancial.spring.*")
public class FinancialServiceApplicationTestConfiguration {
}
