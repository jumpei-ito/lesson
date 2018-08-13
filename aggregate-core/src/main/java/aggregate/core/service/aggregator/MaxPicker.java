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
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeys;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.ColumnSetConverter;
import aggregate.core.service.ColumnSetSorter;
import aggregate.core.util.CheckUtils;
import aggregate.core.util.FunctionUtils;

@Component
public class MaxPicker {

  @Autowired
  private ColumnSetConverter converter;
  @Autowired
  private ColumnSetSorter sorter;

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
      throw new RuntimeException(
          "Target column type mast be BigDecimal: " + aggregateKey.toString());
    }
    return Collectors.collectingAndThen(Collectors.maxBy(getComparator(aggregateKey)),
        Optional::get);
  }

  private Comparator<ColumnSet> getComparator(BaseSheetHeader header) {
    return (columnSet1, columnSet2) -> columnSet1.getColumn(header)
        .compareTo(columnSet2.getColumn(header));
  }

}
