package aggregate.core.common

import static org.junit.Assert.*
import aggregate.core.AggregateApplication
import aggregate.core.TestConfig
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.io.CsvReader
import aggregate.core.io.Writer
import aggregate.core.model.ColumnSet
import spock.lang.Specification

class BaseAggregateSpecification extends Specification {

  static Map<String, List<ColumnSet>> files = new HashMap()
  def comparator = new ColumnSetComparator()

  AggregateApplication application
  CsvReader reader
  Writer writer
  //  Counter counter


  def setup() {
    // Spring boot up
    application = new AggregateApplication(TestConfig.class)
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

  def readOriginalFile(AggregateParameters params) {
    readFile(params.originalHeaders, params.originalFilePath, null)
  }

  def readExpectedFile(AggregateParameters params) {
    readFile(params.expectedHeaders, params.expectedFilePath, null)
  }

  def readExpectedFile(AggregateParameters params, String quote) {
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

}
