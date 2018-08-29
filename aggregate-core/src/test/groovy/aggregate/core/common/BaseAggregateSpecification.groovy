package aggregate.core.common

import static org.junit.Assert.*
import aggregate.core.AggregateApplication
import aggregate.core.TestConfig
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.ColumnType
import aggregate.core.exception.MissingColumnTypeException
import aggregate.core.io.CsvReader
import aggregate.core.io.Writer
import aggregate.core.model.ColumnSet
import spock.lang.Specification

class BaseAggregateSpecification extends Specification {

  /** Spring boot up */
  static final AggregateApplication application = new AggregateApplication(TestConfig.class)
  //**  */
  static Map<String, List<ColumnSet>> files = new HashMap()

  def comparator = new ColumnSetComparator()
  CsvReader reader
  Writer writer

  def setup() {
    // create components
    reader = application.getBean(CsvReader.class)
    writer = application.getBean(Writer.class)
  }

  def compare(List<ColumnSet> expected, List<ColumnSet> aggregated) {
    comparator.compare(expected, aggregated)
  }

  void assertCompareResult(CompareResult result) {
    if (result.isDifferent) {
      printDifference(result.diffs)
    }
    assertFalse(result.isDifferent)
  }

  def readOriginalFile(BaseParameters params) {
    readFile(params.originalHeaders, params.originalFilePath, null)
  }

  def readExpectedFile(BaseParameters params) {
    readFile(params.expectedHeaders, params.expectedFilePath, null)
  }

  def readExpectedFile(BaseParameters params, String quote) {
    readFile(params.expectedHeaders, params.expectedFilePath, quote)
  }

  def readFile(BaseSheetHeader[] headers, String filePath, String quote) {
    if (files.containsKey(filePath)) {
      return files.get(filePath)
    } else {
      def columnSets = quote == null || quote.length() == 0 ?
          reader.read(headers, filePath) : reader.read(headers, filePath, quote)
      files.put(filePath, columnSets)
      return columnSets
    }
  }

  def printDifference(List<String> diffs) {
    println("Differences between aggregated result and expected result.")
    println("[row] Expected Line <--> Actual Line")
    diffs.each { println it }
  }

  void assertMissingColumnTypeException(MissingColumnTypeException e, ColumnType expectedType) {
    println(e)
    assertTrue(e.getExpected().equals(expectedType))
  }

}
