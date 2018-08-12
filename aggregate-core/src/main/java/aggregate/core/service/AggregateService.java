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
    List<BaseAggregator> aggregaters = application.getAggregaters();
    execute(aggregaters, columnSets);
  }

  private List<ColumnSet> readCsvFile(AggregateApplication application) {
    String csvFilePath = application.getProperty(Constant.P_CSV_FILE_PATH);
    BaseSheetHeader[] sheetHeader = application.getBean(BaseSheetHeader[].class);
    return reader.read(sheetHeader, csvFilePath);
  }

  private void execute(List<BaseAggregator> aggregaters, List<ColumnSet> columnSets) {
    aggregaters.forEach(aggregater -> execute(aggregater, columnSets));
  }

  private void execute(BaseAggregator aggregater, List<ColumnSet> columnSets) {
    String aggregaterName = aggregater.getClass().getSimpleName();
    System.out.println(String.format("- Start %s.", aggregaterName));
    // aggregate
    List<ColumnSet> result = aggregater.aggregate(columnSets);
    // TODO: output to csv file
    writer.write(result);
    System.out.println(String.format("- End %s.", aggregaterName));
  }

}
