package aggregate.core.service.converter

import aggregate.core.Constant
import aggregate.core.common.BaseAggregateSpecification
import aggregate.core.common.BaseParameters
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.TestOutputHeader
import aggregate.core.constant.TestSalesSheetHeader
import aggregate.core.service.ColumnSetConverter
import spock.lang.Unroll

class MergeColumnSetSpecification extends BaseAggregateSpecification {

  ColumnSetConverter converter

  def setup() {
    converter = application.getBean(ColumnSetConverter.class)
  }

  @Unroll
  def "Merge #title"() {
    setup:
    // read original csv file
    def to = readFile(params.toHeaders, params.toFilePath, null)
    def from = readFile(params.fromHeaders, params.fromFilePath, null)
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // merge
    def merged = converter.merge(to, from, params.primaryKey, params.after)

    when:
    def result = compare(expected, merged)

    then:
    assertCompareResult(result)

    where:
    title            | params
    "by Primary Key" | params1
  }

  @Unroll
  def "Merge to Right #title"() {
    setup:
    // read original csv file
    def to = readFile(params.toHeaders, params.toFilePath, null)
    def from = readFile(params.fromHeaders, params.fromFilePath, null)
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // merge
    def merged = converter.mergeToRight(to, from)

    when:
    def result = compare(expected, merged)

    then:
    assertCompareResult(result)

    where:
    title         | params
    "Two Columns" | params2
  }

  def getParams1() {
    new MergeParameters(
        expectedHeaders: expectedHeaders1,
        expectedFilePath: "bin/converter/merge-01-result.csv",
        toHeaders: toHeaders,
        toFilePath: "bin/converter/merge-01-original-to.csv",
        fromHeaders: fromHeaders,
        fromFilePath: "bin/converter/merge-01-original-from.csv",
        primaryKey: primaryKey,
        after: TestSalesSheetHeader.PERSON)
  }

  def getExpectedHeaders1() {
    BaseSheetHeader[] header =
        [
          TestSalesSheetHeader.PERSON,
          TestOutputHeader.EXPECT_PERSONAL_TOTAL_AMOUNT,
          TestOutputHeader.COUNT,
          TestSalesSheetHeader.ITEM_CODE,
          TestOutputHeader.EXPECT_AMOUNT
        ]
  }

  def getToHeaders() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestSalesSheetHeader.ITEM_CODE, TestSalesSheetHeader.AMOUNT]
  }

  def getFromHeaders() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestOutputHeader.PERSONAL_TOTAL_AMOUNT, TestOutputHeader.COUNT]
  }

  def getPrimaryKey() {
    Arrays.asList(TestSalesSheetHeader.PERSON)
  }

  def getParams2() {
    new MergeParameters(
        expectedHeaders: expectedHeaders2,
        expectedFilePath: "bin/converter/merge-02-result.csv",
        toHeaders: toHeaders2,
        toFilePath: "bin/converter/merge-02-original-to.csv",
        fromHeaders: fromHeaders2,
        fromFilePath: "bin/converter/merge-02-original-from.csv")
  }

  def getExpectedHeaders2() {
    BaseSheetHeader[] header =
        [
          TestSalesSheetHeader.PERSON,
          TestSalesSheetHeader.ITEM_CODE,
          TestOutputHeader.COUNT,
          TestOutputHeader.EXPECT_AMOUNT,
          TestOutputHeader.EXPECT_PERSONAL_AMOUNT_RATE
        ]
  }

  def getToHeaders2() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestSalesSheetHeader.ITEM_CODE, TestOutputHeader.COUNT]
  }

  def getFromHeaders2() {
    BaseSheetHeader[] header =
        [
          TestSalesSheetHeader.PERSON,
          TestSalesSheetHeader.ITEM_CODE,
          TestSalesSheetHeader.AMOUNT,
          TestOutputHeader.EXPECT_PERSONAL_AMOUNT_RATE
        ]
  }

  class MergeParameters extends BaseParameters {
    /**  */
    BaseSheetHeader[] toHeaders
    /**  */
    String toFilePath
    /**  */
    BaseSheetHeader[] fromHeaders
    /**  */
    String fromFilePath
    /**  */
    List<BaseSheetHeader> primaryKey
    /**  */
    BaseSheetHeader after
  }
}
