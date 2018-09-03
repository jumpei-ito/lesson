package aggregate.core.service.aggregator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.ColumnSetConverter;
import aggregate.core.service.ColumnSetSorter;
import aggregate.core.util.FunctionUtils;

/**
 * Aggregator class to count specified rows.
 */
@Component
public class Counter {

  @Autowired
  private ColumnSetConverter converter;
  @Autowired
  private ColumnSetSorter sorter;

  /**
   * Counts rows count by argument conditions and returns aggregated ColumnSet list.
   *
   * @param columnSets List of ColumnSet to be aggregated
   * @param builder Builder of some grouping keys
   * @param aggregateKey Column header to be counted
   * @param sortKeys Sort keys
   * @return Aggregated ColumnSet list
   */
  public List<ColumnSet> execute(List<ColumnSet> columnSets, GroupingKeysBuilder builder,
      BaseSheetHeader aggregateKey, List<SortKey> sortKeys) {
    // count
    Map<GroupingKeys, BigDecimal> tmpResult = columnSets.stream()
        .collect(Collectors.groupingBy(FunctionUtils.getGroupingKeys(builder), counting()));
    // convert to columnSet
    List<ColumnSet> convertedResult = converter.convert(tmpResult, aggregateKey);
    // sort columnSet
    return sorter.sortColumnSets(convertedResult, sortKeys);
  }

  private Collector<ColumnSet, ?, BigDecimal> counting() {
    return Collectors.reducing(BigDecimal.ZERO, columnSet -> BigDecimal.ONE, BigDecimal::add);
  }

}
