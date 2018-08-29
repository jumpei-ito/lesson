package aggregate.core.service.aggregator

import aggregate.core.Constant
import aggregate.core.common.AggregateParameters
import aggregate.core.common.BaseAggregateSpecification
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.ColumnType
import aggregate.core.constant.SortType
import aggregate.core.constant.TestOutputHeader
import aggregate.core.constant.TestSalesSheetHeader
import aggregate.core.exception.MissingColumnTypeException
import aggregate.core.model.AggregateKey
import aggregate.core.model.ColumnSet
import aggregate.core.model.SortKey
import aggregate.core.model.grouping.GroupingKeysBuilder
import aggregate.core.util.FunctionUtils
import spock.lang.Unroll

class SummarizerSpecification extends BaseAggregateSpecification {

  Summarizer summarizer
  List<ColumnSet> original

  def setup() {
    summarizer = application.getBean(Summarizer.class)
    original = readFile(Constant.ORIGINAL_HEADERS, Constant.ORIGINAL_FILE_PATH, null)
  }

  @Unroll
  def "Summary #title"() {
    setup:
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // aggregate
    def aggregated = summarizer.execute(original, params.groupingKeyBuilder,
        aggregateKey, params.sortKeys)

    when:
    def result = compare(expected, aggregated)

    then:
    assertCompareResult(result)

    where:
    title                   | params  | aggregateKey
    "Amount by Two Keys"    | params1 | params1.aggregateKeyHeader
    "Amount by Month"       | params2 | params2.aggregateKey
    "Amount by Day of Week" | params3 | params3.aggregateKeyHeader
  }

  def "Throwing Missing Column Type Exception"() {
    setup:
    def params = exceptionParams

    when:
    summarizer.execute(original, params.groupingKeyBuilder, params.aggregateKeyHeader,
        params.sortKeys)

    then:
    def e = thrown(MissingColumnTypeException)
    assertMissingColumnTypeException(e, ColumnType.BIGDECIMAL)
  }

  def getParams1() {
    AggregateParameters.builder()
        .expectedHeaders(expectedHeaders1)
        .expectedFilePath("bin/summarizer/summarizer-result-01.csv")
        .groupingKeyBuilder(Constant.BUILDER_GROUING_BY_PERSON_AND_ITEM)
        .aggregateKeyHeader(TestSalesSheetHeader.AMOUNT)
        .sortKeys(Constant.SORT_KEY_BY_PERSON_AND_ITEM).build()
  }

  def getExpectedHeaders1() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestSalesSheetHeader.ITEM_CODE, TestOutputHeader.EXPECT_AMOUNT]
  }

  def getParams2() {
    AggregateParameters.builder()
        .expectedHeaders(expectedHeaders2)
        .expectedFilePath("bin/summarizer/summarizer-result-02.csv")
        .groupingKeyBuilder(groupingKeyBuilder2)
        .aggregateKey(aggregateKey2)
        .sortKeys(sortKey2).build()
  }

  def getExpectedHeaders2() {
    BaseSheetHeader[] header =
        [TestOutputHeader.EXPECT_MONTH, TestOutputHeader.EXPECT_MONTHLY_TOTAL_AMOUNT]
  }

  def getGroupingKeyBuilder2() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addFunctionKey(FunctionUtils.getMonthGroupingFunction(TestSalesSheetHeader.DATE),
        TestOutputHeader.MONTH);
    return builder;
  }

  def getAggregateKey2() {
    new AggregateKey(TestSalesSheetHeader.AMOUNT, TestOutputHeader.MONTHLY_TOTAL_AMOUNT)
  }

  def getSortKey2() {
    Arrays.asList(new SortKey(TestOutputHeader.MONTH, SortType.ASC))
  }

  def getParams3() {
    AggregateParameters.builder()
        .expectedHeaders(expectedHeaders3)
        .expectedFilePath("bin/summarizer/summarizer-result-03.csv")
        .groupingKeyBuilder(groupingKeyBuilder3)
        .aggregateKeyHeader(TestSalesSheetHeader.AMOUNT)
        .sortKeys(sortKey3)
        .build()
  }

  def getExpectedHeaders3() {
    BaseSheetHeader[] header =
        [TestOutputHeader.EXPECT_DAY_OF_WEEK, TestOutputHeader.EXPECT_AMOUNT]
  }

  def getGroupingKeyBuilder3() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addFunctionKey(FunctionUtils.getDayOfWeekGroupingFunction(TestSalesSheetHeader.DATE),
        TestOutputHeader.DAY_OF_WEEK);
    return builder;
  }

  def getSortKey3() {
    Arrays.asList(new SortKey(TestOutputHeader.DAY_OF_WEEK, SortType.ASC))
  }

  def getExceptionParams() {
    AggregateParameters.builder()
        .groupingKeyBuilder(Constant.BUILDER_GROUING_BY_PERSON_AND_ITEM)
        .aggregateKeyHeader(TestSalesSheetHeader.DATE)
        .sortKeys(Constant.SORT_KEY_BY_PERSON_AND_ITEM).build()
  }
}
