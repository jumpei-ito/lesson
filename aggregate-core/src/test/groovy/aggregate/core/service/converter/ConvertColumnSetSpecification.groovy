package aggregate.core.service.converter

import java.util.stream.Collectors
import aggregate.core.AggregateApplication
import aggregate.core.common.BaseAggregateSpecification
import aggregate.core.common.BaseParameters
import aggregate.core.common.Constant
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.TestOutputHeader
import aggregate.core.constant.TestSalesSheetHeader
import aggregate.core.model.ColumnSet
import aggregate.core.model.grouping.GroupingKeysBuilder
import aggregate.core.service.ColumnSetConverter
import aggregate.core.service.aggregator.MaxPicker
import aggregate.core.service.aggregator.Summarizer
import aggregate.core.util.FunctionUtils
import spock.lang.Shared
import spock.lang.Unroll

class ConvertColumnSetSpecification extends BaseAggregateSpecification {

  static final String ORIGINAL_FILE_PATH_FOR_CONVERT = "bin/converter/convert-01-original.csv"
  @Shared
  ColumnSetConverter converter

  def setupSpec() {
    converter = application.getBean(ColumnSetConverter.class)
  }

  @Unroll
  def "Convert #title"() {
    setup:
    // read original csv file
    def original = readOriginalFile(params)
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // aggregate
    def grouping = params.getGroupingData(original, application)

    when:
    def converted = converter.convert(grouping, params.headerData)
    def result = compare(expected, converted)

    then:
    assertCompareResult(result)

    where:
    title                                        | params
    "Map<GroupingKeys, BigDecimal> -> ColumnSet" | bigDecimalParams
    "Map<GroupingKeys, ColumnSet> -> ColumnSet"  | columnSetParams
  }

  def getBigDecimalParams() {
    new ConvertByHeaderParameters(
        originalHeaders: originalHeaders,
        originalFilePath: ORIGINAL_FILE_PATH_FOR_CONVERT,
        expectedHeaders: expectedHeaders,
        expectedFilePath: "bin/converter/convert-01-result.csv",
        groupingKeyBuilder: groupingKeyBuilder,
        aggregateKey: TestSalesSheetHeader.AMOUNT)
  }

  def getOriginalHeaders() {
    BaseSheetHeader[] header =
        [
          TestSalesSheetHeader.PERSON,
          TestOutputHeader.PERSONAL_TOTAL_AMOUNT,
          TestSalesSheetHeader.ITEM_CODE,
          TestSalesSheetHeader.AMOUNT
        ]
  }

  def getGroupingKeyBuilder() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder()
    builder.addHeaderKey(TestSalesSheetHeader.PERSON)
    return builder
  }

  def getExpectedHeaders() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestOutputHeader.EXPECT_AMOUNT]
  }

  def getColumnSetParams() {
    new ConvertByHeadersParameters(
        originalHeaders: originalHeaders,
        originalFilePath: ORIGINAL_FILE_PATH_FOR_CONVERT,
        expectedHeaders: expectedHeaders,
        expectedFilePath: "bin/converter/convert-02-result.csv",
        groupingKeyBuilder: groupingKeyBuilder,
        aggregateKey: TestSalesSheetHeader.AMOUNT,
        outputHeaders: outputHeaders)
  }

  def getOutputHeaders() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestSalesSheetHeader.AMOUNT]
  }

  abstract class convertParameters extends BaseParameters {
    /**  */
    BaseSheetHeader aggregateKey
    /**  */
    def groupingKeyBuilder
    /**  */
    abstract def getGroupingData(List<ColumnSet> original, AggregateApplication application)
    /**  */
    abstract def getHeaderData()
  }
  class ConvertByHeaderParameters extends convertParameters {

    def getGroupingData(List<ColumnSet> original, AggregateApplication application) {
      original.stream().collect(
          Collectors.groupingBy(FunctionUtils.getGroupingKeys(groupingKeyBuilder),
          application.getBean(Summarizer.class).getSummaryCollector(aggregateKey)))
    }

    def getHeaderData() {
      return aggregateKey
    }
  }

  class ConvertByHeadersParameters extends convertParameters {
    /**  */
    List<BaseSheetHeader> outputHeaders

    def getGroupingData(List<ColumnSet> original, AggregateApplication application) {
      original.stream().collect(
          Collectors.groupingBy(FunctionUtils.getGroupingKeys(groupingKeyBuilder),
          application.getBean(MaxPicker.class).getMaxCollector(aggregateKey)))
    }

    def getHeaderData() {
      return outputHeaders
    }
  }
}
