package aggregate.core.service.aggregator

import aggregate.core.Constant
import aggregate.core.common.AggregateParameters
import aggregate.core.common.BaseAggregateSpecification
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.ColumnType
import aggregate.core.constant.TestOutputHeader
import aggregate.core.constant.TestSalesSheetHeader
import aggregate.core.exception.MissingColumnTypeException
import aggregate.core.model.ColumnSet
import spock.lang.Unroll

class MaxPickerSpecification extends BaseAggregateSpecification {

  MaxPicker picker
  List<ColumnSet> original

  def setup() {
    picker = application.getBean(MaxPicker.class)
    // read original csv file
    original = readFile(Constant.ORIGINAL_HEADERS, Constant.ORIGINAL_FILE_PATH, null)
  }

  @Unroll
  def "#title Test"() {
    setup:
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // aggregate
    def aggregated = picker.execute(original, params.groupingKeyBuilder,
        params.aggregateKeyHeader, params.sortKeys, outputHeaders)

    when:
    def result = compare(expected, aggregated)

    then:
    assertCompareResult(result)

    where:
    title                        | params  | outputHeaders
    "Get Max Amount By Two Keys" | params1 | outputHeaders1
  }

  def "Throwing Missing Column Type Exception Test"() {
    setup:
    def params = exceptionParams

    when:
    picker.execute(original, params.groupingKeyBuilder, params.aggregateKeyHeader,
        params.sortKeys, outputHeaders1)

    then:
    def e = thrown(MissingColumnTypeException)
    println(e)
    e.getExpected().equals(ColumnType.BIGDECIMAL)
  }

  def getParams1() {
    new AggregateParameters(
        originalHeaders: Constant.ORIGINAL_HEADERS,
        originalFilePath: Constant.ORIGINAL_FILE_PATH,
        expectedHeaders: expectedHeaders1,
        expectedFilePath: "bin/maxPicker/maxPicker-result-01.csv",
        groupingKeyBuilder: Constant.BUILDER_GROUING_BY_PERSON_AND_ITEM,
        aggregateKeyHeader: TestSalesSheetHeader.AMOUNT,
        sortKeys: Constant.SORT_KEY_BY_PERSON_AND_ITEM)
  }

  def getExpectedHeaders1() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestSalesSheetHeader.ITEM_CODE, TestOutputHeader.EXPECT_AMOUNT]
  }

  def getOutputHeaders1() {
    Arrays.asList(TestSalesSheetHeader.PERSON, TestSalesSheetHeader.ITEM_CODE,
        TestSalesSheetHeader.AMOUNT)
  }

  def getExceptionParams() {
    new AggregateParameters(
        originalHeaders: Constant.ORIGINAL_HEADERS,
        originalFilePath: Constant.ORIGINAL_FILE_PATH,
        expectedHeaders: expectedHeaders1,
        expectedFilePath: "bin/maxPicker/maxPicker-result-01.csv",
        groupingKeyBuilder: Constant.BUILDER_GROUING_BY_PERSON_AND_ITEM,
        aggregateKeyHeader: TestSalesSheetHeader.DATE,
        sortKeys: Constant.SORT_KEY_BY_PERSON_AND_ITEM)
  }
}