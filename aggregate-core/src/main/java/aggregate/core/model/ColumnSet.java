package aggregate.core.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    addColumn(header, column, columns.size());
  }

  private void addColumn(BaseSheetHeader header, Column column, int colNo) {
    // TODO: duplicate colNo check
    Column newColumn = column.clone();
    newColumn.setNo(colNo);
    columns.put(header, newColumn);
  }

  public void insertColumn(BaseSheetHeader header, BaseSheetHeader after, Column column) {
    // TODO: duplicate header check
    int afterColumnNo = columns.get(after).getNo();
    incrementColumnNo(afterColumnNo);
    addColumn(header, column, afterColumnNo + 1);
  }

  private void incrementColumnNo(int afterColumnNo) {
    columns.values().stream().filter(c -> c.getNo() > afterColumnNo).collect(Collectors.toList())
        .forEach(c -> c.setNo(c.getNo() + 1));
  }

  public List<BaseSheetHeader> getHeaders() {
    return columns.keySet().stream()
        .sorted((h1, h2) -> getColumn(h1).getNo() - getColumn(h2).getNo())
        .collect(Collectors.toList());
  }

  public boolean containsHeader(BaseSheetHeader header) {
    return columns.containsKey(header);
  }

  public boolean containsHeaders(List<BaseSheetHeader> headers) {
    Optional<BaseSheetHeader> result =
        headers.stream().filter(header -> columns.containsKey(header)).findFirst();
    return result.isPresent();
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    columns.entrySet().stream().sorted((e1, e2) -> e1.getValue().getNo() - e2.getValue().getNo())
        .forEach(e -> sb.append(e.getValue().getOutputValue()).append(Constant.COMMA));
    return sb.substring(0, sb.length() - 1);
  }

  public int compareTo(ColumnSet columnSet, List<BaseSheetHeader> headers) {
    if (!containsHeaders(headers) || !columnSet.containsHeaders(headers)) {
      throw new RuntimeException("Illegal headers: " + headers);
    }
    // TODO: fix compare logic to equals and if one header is false then return.
    return headers.stream()
        .mapToInt(header -> columnSet.getColumn(header).compareTo(this.getColumn(header))).sum();
  }

}
