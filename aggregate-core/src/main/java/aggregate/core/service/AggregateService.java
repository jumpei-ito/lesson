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
import aggregate.core.util.AggregateLogger;

/**
 * Service class to call aggregator classes and output aggregate results.
 */
@Component
public class AggregateService {

  @Autowired
  private CsvReader reader;
  @Autowired
  private Writer writer;

  /**
   * Calls Aggregators implements {@link BaseAggregator} and output aggregate result by writer class
   * extends {@link Writer}.
   *
   * @param application Spring context
   */
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
    AggregateLogger.info(String.format("- Start %s.", aggregatorName));
    // aggregate
    List<ColumnSet> result = aggregator.aggregate(columnSets);
    // TODO: output to csv file
    writer.write(result);
    AggregateLogger.info(String.format("- End %s.", aggregatorName));
  }

}
