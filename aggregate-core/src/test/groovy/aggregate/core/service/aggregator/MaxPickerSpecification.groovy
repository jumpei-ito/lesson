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
  def "#title"() {
    setup:
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // aggregate
    def aggregated = picker.execute(original, params.groupingKeyBuilder,
        params.aggregateKeyHeader, params.sortKeys, params.outputHeaders)

    when:
    def result = compare(expected, aggregated)

    then:
    assertCompareResult(result)

    where:
    title                        | params
    "Get Max Amount by Two Keys" | params1
  }

  def "Throwing Missing Column Type Exception"() {
    setup:
    def params = exceptionParams

    when:
    picker.execute(original, params.groupingKeyBuilder, params.aggregateKeyHeader,
        params.sortKeys, params.outputHeaders)

    then:
    def e = thrown(MissingColumnTypeException)
    assertMissingColumnTypeException(e, ColumnType.BIGDECIMAL)
  }

  def getParams1() {
    AggregateParameters.builder()
        .expectedHeaders(expectedHeaders1)
        .expectedFilePath("bin/maxPicker/maxPicker-01-result.csv")
        .groupingKeyBuilder(Constant.BUILDER_GROUING_BY_PERSON_AND_ITEM)
        .aggregateKeyHeader(TestSalesSheetHeader.AMOUNT)
        .sortKeys(Constant.SORT_KEY_BY_PERSON_AND_ITEM)
        .outputHeaders(outputHeaders1).build()
  }

  def getExpectedHeaders1() {
    BaseSheetHeader[] header =
        [
          TestSalesSheetHeader.PERSON,
          TestSalesSheetHeader.ITEM_CODE,
          TestOutputHeader.EXPECT_AMOUNT
        ]
  }

  def getOutputHeaders1() {
    Arrays.asList(TestSalesSheetHeader.PERSON, TestSalesSheetHeader.ITEM_CODE,
        TestSalesSheetHeader.AMOUNT)
  }

  def getExceptionParams() {
    AggregateParameters.builder()
        .groupingKeyBuilder(Constant.BUILDER_GROUING_BY_PERSON_AND_ITEM)
        .aggregateKeyHeader(TestSalesSheetHeader.DATE)
        .sortKeys(Constant.SORT_KEY_BY_PERSON_AND_ITEM)
        .outputHeaders(outputHeaders1).build()
  }
}
