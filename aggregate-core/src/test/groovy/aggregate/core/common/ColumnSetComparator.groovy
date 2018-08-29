package aggregate.core.common

import static org.junit.Assert.*
import aggregate.core.io.StdOutWriter
import aggregate.core.io.Writer
import aggregate.core.model.ColumnSet

class ColumnSetComparator {

  private static String EMPTY_LINE = "Empty Line"
  Writer writer = new StdOutWriter()

  CompareResult compare(List<ColumnSet> expected, List<ColumnSet> aggregated) {
    CompareResult result = new CompareResult(getHeaderDiff(expected, aggregated))
    for (int i = 0; i < expected.size(); i++) {
      compareLine(i, expected.get(i), getAggregatedColumnSet(i, aggregated), result)
    }
    // The case: aggregate result size is more than expected one.
    if (expected.size() < aggregated.size()) {
      setDiffLine(aggregated.size() - expected.size(), aggregated, result)
    }
    return result
  }

  private ColumnSet getAggregatedColumnSet(int index, List<ColumnSet> aggregated) {
    index > aggregated.size() - 1 ? null : aggregated.get(index)
  }

  private String getHeaderDiff(List<ColumnSet> expected,
      List<ColumnSet> aggregated) {
    String originalHeader = writer.getHeaderLine(expected.get(0))
    String aggregatedHeader = writer.getHeaderLine(aggregated.get(0))
    getDiffString(1, originalHeader, aggregatedHeader)
  }

  private void compareLine(int index, ColumnSet expected, ColumnSet aggregated,
      CompareResult result) {
    String expectedStr = expected.toString()
    String aggregatedStr = aggregated == null ? EMPTY_LINE : aggregated.toString()

    if (!expectedStr.equals(aggregatedStr)) {
      // There is two distance from List index to csv file row (header line + starting from 0).
      result.addDiff(getDiffString(index + 2, expectedStr, aggregatedStr))
    }
  }

  private String getDiffString(int row, String expected, String aggregated) {
    String.format("[%d] %s <--> %s", row, expected, aggregated)
  }

  private void setDiffLine(int distance, List<ColumnSet> aggregated, CompareResult result) {
    int start = aggregated.size() - distance
    for(int i = start; i < aggregated.size(); i++) {
      // There is two distance from List index to csv file row (header line + starting from 0)
      result.addDiff(getDiffString(i + 2, EMPTY_LINE, aggregated.get(i).toString()))
    }
  }
}
