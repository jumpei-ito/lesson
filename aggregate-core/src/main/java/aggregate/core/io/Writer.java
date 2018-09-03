package aggregate.core.io;

import java.util.List;
import aggregate.core.constant.Constant;
import aggregate.core.model.ColumnSet;

/**
 * Abstract class for writing aggregated data.
 */
public abstract class Writer {

  /**
   * Write argument List of {@link ColumnSet}.
   *
   * @param columnSets List of {@link ColumnSet}
   */
  public abstract void write(List<ColumnSet> columnSets);

  /**
   * Getter of header line as output String.
   *
   * @param columnSet {@link ColumnSet}
   * @return Header line
   */
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
