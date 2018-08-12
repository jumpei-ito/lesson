package sales.aggregater;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import aggregate.core.AggregateConfig;
import aggregate.core.constant.BaseSheetHeader;
import sales.aggregater.constant.SalesSheetHeader;

@Configuration
@ComponentScan("sales.aggregater")
public class SalesAggregateConfig extends AggregateConfig {

  @Override
  public String csvFilePath() {
    return "bin/SalesList.csv";
  }

  @Override
  public BaseSheetHeader[] csvFileHeader() {
    return SalesSheetHeader.values();
  }

}
