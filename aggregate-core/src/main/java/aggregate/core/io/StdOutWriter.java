package aggregate.core.io;

import java.util.List;
import aggregate.core.model.ColumnSet;

/**
 * Class for writing aggregated data to standard output.
 */
public class StdOutWriter extends Writer {

  @Override
  public void write(List<ColumnSet> columnSets) {
    System.out.println("-- Start output result.");
    writeResult(columnSets);
    System.out.println("-- End output result.");
  }

  private void writeResult(List<ColumnSet> columnSets) {
    if (columnSets.size() == 0) {
      System.out.println("--- ColumnSet is empty.");
    } else {
      System.out.println(getHeaderLine(columnSets.get(0)));
      columnSets.forEach(System.out::println);
    }
  }

}
