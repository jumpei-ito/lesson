package aggregate.core.io;

import java.util.List;

import aggregate.core.constant.Constant;
import aggregate.core.model.ColumnSet;

/**
 * 集計結果データを出力する抽象クラス
 */
public abstract class Writer {

  /**
   * 指定された{@link ColumnSet}のデータを出力する
   *
   * @param columnSets {@link ColumnSet}のList
   */
  public abstract void write(List<ColumnSet> columnSets);

  protected String getHeaderLine(ColumnSet columnSet) {
    StringBuffer sb = new StringBuffer();
    columnSet.getHeaders().forEach(
        header -> sb.append(getOutputString(header.getHeaderName())).append(Constant.COMMA));
    return sb.substring(0, sb.length() - 1);
  }

  private String getOutputString(String value) {
    return String.format("%s%s%s", Constant.DOUBLE_QUOTATION, value, Constant.DOUBLE_QUOTATION);
  }

}
