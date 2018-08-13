package aggregate.core.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aggregate.core.AggregateApplication;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.Constant;
import aggregate.core.io.CsvReader;
import aggregate.core.io.Writer;
import aggregate.core.model.ColumnSet;

@Component
public class AggregateService {

  @Autowired
  CsvReader reader;
  @Autowired
  private Writer writer;

  public void aggregate(AggregateApplication application) {
    List<ColumnSet> columnSets = readCsvFile(application);
    List<BaseAggregator> aggregators = application.getAggregators();
    execute(aggregators, columnSets);
  }

  private List<ColumnSet> readCsvFile(AggregateApplication application) {
    // get header from config -> csvFileHeader()
    BaseSheetHeader[] sheetHeader = application.getBean(BaseSheetHeader[].class);
    String csvFilePath = application.getProperty(Constant.P_CSV_FILE_PATH);
    return reader.read(sheetHeader, csvFilePath);
  }

  private void execute(List<BaseAggregator> aggregators, List<ColumnSet> columnSets) {
    aggregators.forEach(aggregator -> execute(aggregator, columnSets));
  }

  private void execute(BaseAggregator aggregator, List<ColumnSet> columnSets) {
    String aggregatorName = aggregator.getClass().getSimpleName();
    System.out.println(String.format("- Start %s.", aggregatorName));
    // aggregate
    List<ColumnSet> result = aggregator.aggregate(columnSets);
    // TODO: output to csv file
    writer.write(result);
    System.out.println(String.format("- End %s.", aggregatorName));
  }

}
