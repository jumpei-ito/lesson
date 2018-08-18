package aggregate.core.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.Constant;
import aggregate.core.model.column.BigDecimalColumn;
import aggregate.core.model.column.Column;
import aggregate.core.model.column.DateColumn;
import aggregate.core.model.column.DayOfWeekColumn;
import aggregate.core.model.column.MonthColumn;
import aggregate.core.model.column.StringColumn;


public class ColumnSet {

  private Map<BaseSheetHeader, Column> columns = new LinkedHashMap<>();

  public ColumnSet() {
    super();
  }

  public ColumnSet(List<BaseSheetHeader> headers, String[] values) {
    if (headers.size() != values.length) {
      throw new RuntimeException(String.format(
          "Missmatch header and body count. header: %d, body: %d", headers.size(), values.length));
    }
    headers
        .forEach(header -> columns.put(header, getColumn(headers.indexOf(header), header, values)));
  }

  private Column getColumn(int no, BaseSheetHeader header, String[] values) {
    return getColumn(no, header, values[no]);
  }

  private Column getColumn(int no, BaseSheetHeader header, String value) {
    switch (header.getColumnType()) {
      case STRING:
        return new StringColumn(no, value);
      case BIGDECIMAL:
        return new BigDecimalColumn(no, value, Constant.NUMBER_FORMAT);
      case DATE:
        return new DateColumn(no, value);
      case MONTH:
        return new MonthColumn(no, value);
      case DAY_OF_WEEK:
        return new DayOfWeekColumn(no, value);
      case CURRENCY:
        return new BigDecimalColumn(no, value, Constant.CURRENCY_FORMAT);
      case PERCENT:
        return new BigDecimalColumn(no, value, Constant.PERCENT_FORMAT);
      default:
        throw new RuntimeException("Illigal column type: " + header.getColumnType().toString());
    }
  }

  public Column getColumn(BaseSheetHeader header) {
    if (!containsHeader(header)) {
      throw new RuntimeException("Not contains column of header:" + header.toString());
    }
    return columns.get(header);
  }

  public void addColumn(BaseSheetHeader header, String value) {
    // TODO: duplicate header check
    columns.put(header, getColumn(columns.size(), header, value));
  }

  public void addColumn(BaseSheetHeader header, Column column) {
    // TODO: duplicate header check
    Column newColumn = column.clone();
    newColumn.setNo(columns.size());
    columns.put(header, newColumn);
  }

  public List<BaseSheetHeader> getHeaders() {
    return columns.keySet().stream()
        .sorted((h1, h2) -> getColumn(h1).getNo() - getColumn(h2).getNo())
        .collect(Collectors.toList());
  }

  public boolean containsHeader(BaseSheetHeader header) {
    return columns.containsKey(header);
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    columns.entrySet().stream().sorted((e1, e2) -> e1.getValue().getNo() - e2.getValue().getNo())
        .forEach(e -> sb.append(e.getValue().getOutputValue()).append(Constant.COMMA));
    return sb.substring(0, sb.length() - 1);
  }

}
