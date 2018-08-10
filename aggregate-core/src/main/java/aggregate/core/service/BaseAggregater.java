package aggregate.core.service;

import java.util.List;

import aggregate.core.model.ColumnSet;

public interface BaseAggregater {

  int getExecuteOrder();

  List<ColumnSet> aggregate(List<ColumnSet> columnSets);

}
