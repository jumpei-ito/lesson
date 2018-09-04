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
import aggregate.core.util.AggregateLogger
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

  def compare(ExpectedColumnSetList expected, List<ColumnSet> aggregated) {
    comparator.compare(expected, aggregated)
  }

  void assertCompareResult(CompareResult result) {
    if (result.isDifferent) {
      printDifference(result.diffs, result.expectedFilePath)
    }
    assertFalse(result.isDifferent)
  }

  def readOriginalFile(BaseParameters params) {
    readFile(params.originalHeaders, params.originalFilePath, null)
  }

  def readExpectedFile(BaseParameters params) {
    def expected = readFile(params.expectedHeaders, params.expectedFilePath, null)
    new ExpectedColumnSetList(expected, params.expectedFilePath)
  }

  def readExpectedFile(BaseParameters params, String quote) {
    def expected = readFile(params.expectedHeaders, params.expectedFilePath, quote)
    new ExpectedColumnSetList(expected, params.expectedFilePath)
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

  def printDifference(List<String> diffs, String expectedFilePath) {
    String caseTitle = specificationContext.currentIteration.name
    AggregateLogger.info("Start Case: " + caseTitle)
    AggregateLogger.info("- Expected file: " + expectedFilePath)
    AggregateLogger.info("- [row] Expected Line <--> Actual Line")
    diffs.each { AggregateLogger.info("- " + it) }
    AggregateLogger.info("End Case: " + caseTitle)
  }

  void assertMissingColumnTypeException(MissingColumnTypeException e, ColumnType expectedType) {
    def result = e.getExpected().equals(expectedType)
    if (!result) {
      String caseTitle = specificationContext.currentIteration.name
      AggregateLogger.info("Start Case: " + caseTitle)
      AggregateLogger.info("- " + e.getMessage())
      AggregateLogger.info("End Case: " + caseTitle)
    }
    assertTrue(result)
  }

}
