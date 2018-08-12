package aggregate.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.column.Column;
import aggregate.core.model.grouping.GroupingKey;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.util.CheckUtils;

@Component
public class ColumnSetConverter {

  public List<ColumnSet> convert(Map<GroupingKeys, BigDecimal> aggregateResult,
      BaseSheetHeader summaryKey) {
    return aggregateResult.entrySet().stream()
        .map(e -> convertToColumnSet(e.getKey(), e.getValue(), summaryKey))
        .collect(Collectors.toList());
  }

  public List<ColumnSet> convert(Map<GroupingKeys, ColumnSet> aggregateResult,
      List<BaseSheetHeader> headers) {
    return aggregateResult.entrySet().stream().map(e -> convertToColumnSet(e.getValue(), headers))
        .collect(Collectors.toList());
  }

  private ColumnSet convertToColumnSet(ColumnSet originalColumnSet, List<BaseSheetHeader> headers) {
    if (!CheckUtils.containsKeys(originalColumnSet, headers)) {
      throw new RuntimeException("Not contains output headers in ColumnSet.");
    }
    ColumnSet columnSet = new ColumnSet();
    headers.forEach(header -> columnSet.addColumn(header, originalColumnSet.getColumn(header)));
    return columnSet;
  }

  private ColumnSet convertToColumnSet(GroupingKeys keys, BigDecimal summary,
      BaseSheetHeader summaryKey) {
    ColumnSet columnSet = new ColumnSet();
    keys.getKeys().forEach(key -> addColumn(columnSet, key));
    columnSet.addColumn(summaryKey, summary.toString());
    return columnSet;
  }

  private void addColumn(ColumnSet columnSet, GroupingKey key) {
    if (key.canGetValueAsColumn()) {
      columnSet.addColumn(key.getKey(), key.getValueAsColumn());
    } else {
      columnSet.addColumn(key.getKey(), key.getValueAsString());
    }
  }

  public List<ColumnSet> mergeToRight(List<ColumnSet> to, List<ColumnSet> from) {
    List<ColumnSet> result = new ArrayList<>();
    if (to.size() != from.size()) {
      throw new RuntimeException(String
          .format("Missmatch target ColumnSets size. to: %s, from: %s.", to.size(), from.size()));
    }
    for (int i = 0; i < to.size(); i++) {
      result.add(mergeToRight(to.get(i), from.get(i)));
    }
    return result;
  }

  private ColumnSet mergeToRight(ColumnSet to, ColumnSet from) {
    ColumnSet result = new ColumnSet();
    to.getHeaders().forEach(header -> result.addColumn(header, to.getColumn(header)));
    from.getHeaders().forEach(header -> addColumn(result, header, from.getColumn(header)));
    return result;
  }

  private void addColumn(ColumnSet columnSet, BaseSheetHeader header, Column column) {
    if (!columnSet.containsHeader(header)) {
      columnSet.addColumn(header, column);
    }
  }

}
