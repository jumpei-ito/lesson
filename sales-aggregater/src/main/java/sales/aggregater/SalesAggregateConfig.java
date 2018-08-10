package sales.aggregater;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import aggregate.core.AggregateConfig;

@Configuration
@ComponentScan("sales.aggregater.service")
public class SalesAggregateConfig extends AggregateConfig {

  @Override
  public String csvFilePath() {
    return "bin/SalesList.csv";
  }

}
