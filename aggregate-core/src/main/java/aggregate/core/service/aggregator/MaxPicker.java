package aggregate.core.service.aggregator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.ColumnType;
import aggregate.core.exception.MissingColumnTypeException;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.ColumnSetConverter;
import aggregate.core.service.ColumnSetSorter;
import aggregate.core.util.CheckUtils;
import aggregate.core.util.FunctionUtils;

/**
 * Aggregator class to summarize and pick row has max value.
 */
@Component
public class MaxPicker {

  @Autowired
  private ColumnSetConverter converter;
  @Autowired
  private ColumnSetSorter sorter;

  /**
   * Picks row has max value by argument conditions and returns aggregated ColumnSet list.
   *
   * @param columnSets List of ColumnSet to be aggregated
   * @param builders Builder of some grouping keys
   * @param aggregateKey Column header to be picked
   * @param sortKeys Sort keys
   * @param outputHeaders Headers for aggregated ColumnSet list
   * @return Aggregated ColumnSet list
   */
  public List<ColumnSet> execute(List<ColumnSet> columnSets, GroupingKeysBuilder builders,
      BaseSheetHeader aggregateKey, List<SortKey> sortKeys, List<BaseSheetHeader> outputHeaders) {
    // aggregate
    Map<GroupingKeys, ColumnSet> tmpResult = columnSets.stream().collect(Collectors
        .groupingBy(FunctionUtils.getGroupingKeys(builders), getMaxCollector(aggregateKey)));
    // convert to columnSet
    List<ColumnSet> convertedResult = converter.convert(tmpResult, outputHeaders);
    // sort columnSet
    return sorter.sortColumnSets(convertedResult, sortKeys);
  }

  private Collector<ColumnSet, ?, ColumnSet> getMaxCollector(BaseSheetHeader aggregateKey) {
    if (!CheckUtils.isBigDecimalColumnType(aggregateKey)) {
      throw new MissingColumnTypeException(ColumnType.BIGDECIMAL, aggregateKey.getColumnType());
    }
    return Collectors.collectingAndThen(Collectors.maxBy(getComparator(aggregateKey)),
        Optional::get);
  }

  private Comparator<ColumnSet> getComparator(BaseSheetHeader header) {
    return (columnSet1, columnSet2) -> columnSet1.getColumn(header)
        .compareTo(columnSet2.getColumn(header));
  }

}
