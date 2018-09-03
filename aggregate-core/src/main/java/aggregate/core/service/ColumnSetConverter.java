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

/**
 * Class to convert aggregate result and ColumnSet list to new ColumnSet list.
 */
@Component
public class ColumnSetConverter {

  /**
   * Converts aggregate result (GroupingKeys and summary value) to ColumnSet list.
   *
   * @param aggregateResult Aggregate result to be converted
   * @param summaryKey summarized column header
   * @return Converted ColumnSet list
   */
  public List<ColumnSet> convert(Map<GroupingKeys, BigDecimal> aggregateResult,
      BaseSheetHeader summaryKey) {
    return aggregateResult.entrySet().stream()
        .map(e -> convertToColumnSet(e.getKey(), e.getValue(), summaryKey))
        .collect(Collectors.toList());
  }

  /**
   * Converts aggregate result (GroupingKeys and ColumnSet) to ColumnSet list.
   *
   * @param aggregateResult Aggregate result to be converted
   * @param headers Column headers after convert
   * @return Converted ColumnSet list
   */
  public List<ColumnSet> convert(Map<GroupingKeys, ColumnSet> aggregateResult,
      List<BaseSheetHeader> headers) {
    return aggregateResult.entrySet().stream().map(e -> convertToColumnSet(e.getValue(), headers))
        .collect(Collectors.toList());
  }

  private ColumnSet convertToColumnSet(ColumnSet originalColumnSet, List<BaseSheetHeader> headers) {
    if (!originalColumnSet.containsHeaders(headers)) {
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

  /**
   * Merges columns to right of original ColumnSet and returns result.<br>
   * Skips merge the same columns betoween two ColumnSets.
   *
   * @param to Original ColumnSet list
   * @param from ColumnSet list to be merged
   * @return Merged ColumnSet list
   */
  public List<ColumnSet> mergeToRight(List<ColumnSet> to, List<ColumnSet> from) {
    if (to.size() != from.size()) {
      throw new RuntimeException(String
          .format("Missmatch target ColumnSets size. to: %s, from: %s.", to.size(), from.size()));
    }
    List<ColumnSet> result = new ArrayList<>();
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

  /**
   * Merges two ColumnSet lists after specified original column.<br>
   * Skips merge columns of primaryKey.
   *
   * @param to Original ColumnSet list
   * @param from ColumnSet list to be merged
   * @param primaryKey primary key of two ColumnSets
   * @param after Point to merge
   * @return Merged ColumnSet list
   */
  public List<ColumnSet> merge(List<ColumnSet> to, List<ColumnSet> from,
      List<BaseSheetHeader> primaryKey, BaseSheetHeader after) {
    if (to.size() < from.size()) {
      throw new RuntimeException(String.format(
          "Merged ColumnSets size must be more than Merging ColumnSets size. to: %s, from: %s.",
          to.size(), from.size()));
    }
    List<ColumnSet> result = new ArrayList<>();
    from.forEach(columnSet -> result.addAll(mergeColumnSet(to, columnSet, primaryKey, after)));
    return result;
  }

  private List<ColumnSet> mergeColumnSet(List<ColumnSet> to, ColumnSet from,
      List<BaseSheetHeader> primaryKey, BaseSheetHeader after) {
    return to.stream().filter(columnSet -> columnSet.equalsByHeaders(from, primaryKey))
        .map(columnSet -> mergeColumns(columnSet, from, primaryKey, after))
        .collect(Collectors.toList());
  }

  private ColumnSet mergeColumns(ColumnSet to, ColumnSet from, List<BaseSheetHeader> primaryKey,
      BaseSheetHeader after) {
    from.getHeaders().stream().filter(header -> !primaryKey.contains(header))
        .sorted((h1, h2) -> from.getColumn(h2).getNo() - from.getColumn(h1).getNo())
        .forEach(header -> to.insertColumn(header, after, from.getColumn(header)));
    return to;
  }

}
