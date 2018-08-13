package aggregate.core.io;

import java.util.List;
import aggregate.core.model.ColumnSet;

/**
 * 集計結果データを標準出力に出力するクラス
 */
public class StdOutWriter extends Writer {

  @Override
  public void write(List<ColumnSet> columnSets) {
    // TODO: validate columnSets size
    System.out.println("-- Start output result.");
    System.out.println(getHeaderLine(columnSets.get(0)));
    columnSets.forEach(System.out::println);
    System.out.println("-- End output result.");
  }

}
