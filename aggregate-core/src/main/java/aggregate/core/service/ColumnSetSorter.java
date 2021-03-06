package aggregate.core.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;

/**
 * Class to sort list of ColumnSet.
 */
@Component
public class ColumnSetSorter {

  /**
   * Sorts argument list of ColumnSet by sort conditions.
   *
   * @param columnSets List of ColumnSet to be sorted
   * @param sortKeys Sort conditions
   * @return Sorted ColumnSet list
   */
  public List<ColumnSet> sortColumnSets(List<ColumnSet> columnSets, List<SortKey> sortKeys) {
    validateBeforeSort(columnSets, sortKeys);
    return columnSets.stream().sorted(getComparator(sortKeys)).collect(Collectors.toList());
  }

  private void validateBeforeSort(List<ColumnSet> columnSets, List<SortKey> sortKeys) {
    // Create new ArrayList because "Lists.transform" is applied lazily.
    List<BaseSheetHeader> headers = new ArrayList<>(Lists.transform(sortKeys, key -> key.getKey()));
    if (!columnSets.get(0).containsHeaders(headers)) {
      throw new RuntimeException("Not contains sortKeys in ColumnSet.");
    }
  }

  private Comparator<ColumnSet> getComparator(List<SortKey> sortKeys) {
    // Use AtomicReference to update parameter in "List.forEach".
    AtomicReference<Comparator<ColumnSet>> comparator = new AtomicReference<>();
    sortKeys.forEach(sortKey -> comparator.getAndUpdate(c -> addSortKey(c, sortKey)));
    return comparator.get();
  }

  private Comparator<ColumnSet> addSortKey(Comparator<ColumnSet> comparator, SortKey sortKey) {
    Comparator<ColumnSet> tmpComparator = getComparator(sortKey.getKey());
    if (sortKey.isDesc()) {
      tmpComparator = tmpComparator.reversed();
    }
    return comparator == null ? tmpComparator : comparator.thenComparing(tmpComparator);
  }

  private Comparator<ColumnSet> getComparator(BaseSheetHeader header) {
    return (columnSet1, columnSet2) -> columnSet1.getColumn(header)
        .compareTo(columnSet2.getColumn(header));
  }

}
