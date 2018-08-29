package aggregate.core.io;

import java.util.List;
import aggregate.core.model.ColumnSet;

/**
 * 集計結果データを標準出力に出力するクラス
 */
public class StdOutWriter extends Writer {

  @Override
  public void write(List<ColumnSet> columnSets) {
    System.out.println("-- Start output result.");
    if (!writeResult(columnSets)) {
      System.out.println("--- ColumnSet is empty.");
    }
    System.out.println("-- End output result.");
  }

  private boolean writeResult(List<ColumnSet> columnSets) {
    if (columnSets.size() == 0) {
      return false;
    }
    System.out.println(getHeaderLine(columnSets.get(0)));
    columnSets.forEach(System.out::println);
    return true;
  }

}
