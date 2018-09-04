package aggregate.core.common

import aggregate.core.model.ColumnSet

class ExpectedColumnSetList extends ArrayList<ColumnSet> {

  String expectedFilePath

  ExpectedColumnSetList(List<ColumnSet> columnSets, String expectedFilePath) {
    super(columnSets)
    this.expectedFilePath = expectedFilePath
  }
}
