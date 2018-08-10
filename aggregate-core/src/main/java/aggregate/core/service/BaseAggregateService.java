package aggregate.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.io.CsvReader;
import aggregate.core.io.Writer;
import aggregate.core.model.ColumnSet;

public abstract class BaseAggregateService {

  @Autowired
  CsvReader reader;
  @Autowired
  private Writer writer;

  protected abstract BaseSheetHeader[] getSheetHeader();

  public void aggregate(List<BaseAggregater> aggregaters, String filePath) {
    List<ColumnSet> columnSets = reader.read(getSheetHeader(), filePath);
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
