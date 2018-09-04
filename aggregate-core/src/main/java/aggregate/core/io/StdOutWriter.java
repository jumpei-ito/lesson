package aggregate.core.io;

import java.util.List;
import aggregate.core.model.ColumnSet;
import aggregate.core.util.AggregateLogger;

/**
 * Class for writing aggregated data to standard output.
 */
public class StdOutWriter extends Writer {

  @Override
  public void write(List<ColumnSet> columnSets) {
    AggregateLogger.info("-- Start output result.");
    writeResult(columnSets);
    AggregateLogger.info("-- End output result.");
  }

  private void writeResult(List<ColumnSet> columnSets) {
    if (columnSets.size() == 0) {
      AggregateLogger.info("--- ColumnSet is empty.");
    } else {
      // print to stdout because this is not a log
      System.out.println(getHeaderLine(columnSets.get(0)));
      columnSets.forEach(System.out::println);
    }
  }

}
