package aggregate.core;

import java.util.List;
import org.springframework.context.annotation.Bean;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.TestSalesSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.service.BaseAggregator;

public class TestConfig extends AggregateConfig {

  @Override
  public String csvFilePath() {
    return "bin/SalesList.csv";
  }

  @Override
  public BaseSheetHeader[] csvFileHeader() {
    return TestSalesSheetHeader.values();
  }

  @Bean
  public BaseAggregator baseAggregator() {
    return new TestAggregator();
  }

  public class TestAggregator implements BaseAggregator {

    @Override
    public int getExecuteOrder() {
      return 0;
    }

    @Override
    public List<ColumnSet> aggregate(List<ColumnSet> columnSets) {
      return null;
    }

  }

}
