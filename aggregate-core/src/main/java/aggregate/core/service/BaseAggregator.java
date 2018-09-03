package aggregate.core.service;

import java.util.List;
import aggregate.core.model.ColumnSet;

/**
 * Interface for aggregator class.<br>
 * The classes implements this interface are called from {@link AggregateService}.
 */
public interface BaseAggregator {

  /**
   * Getter of execute order in all aggregators.
   *
   * @return Execute order
   */
  int getExecuteOrder();

  /**
   * Aggregates argument ColumnSet and returns aggregate result.
   *
   * @param columnSets List of ColumnSet to be aggregated
   * @return Aggregated ColumnSet list
   */
  List<ColumnSet> aggregate(List<ColumnSet> columnSets);

}
