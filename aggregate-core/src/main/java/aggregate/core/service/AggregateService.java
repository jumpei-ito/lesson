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
    // read csv file to columnSets
    String csvFilePath = application.getProperty(Constant.P_CSV_FILE_PATH);
    BaseSheetHeader[] sheetHeader = application.getBean(BaseSheetHeader[].class);
    List<ColumnSet> columnSets = reader.read(sheetHeader, csvFilePath);
    // aggregate
    List<BaseAggregater> aggregaters = application.getAggregaters();
    execute(aggregaters, columnSets);
  }

  public void execute(List<BaseAggregater> aggregaters, List<ColumnSet> columnSet) {
    aggregaters.forEach(aggregater -> execute(aggregater, columnSet));
  }

  private void execute(BaseAggregater aggregater, List<ColumnSet> columnSet) {
    String aggregaterName = aggregater.getClass().getSimpleName();
    System.out.println(String.format("- Start %s.", aggregaterName));
    // aggregate
    List<ColumnSet> result = aggregater.aggregate(columnSet);
    // TODO: output to csv file
    writer.write(result);
    System.out.println(String.format("- End %s.", aggregaterName));
  }

}
