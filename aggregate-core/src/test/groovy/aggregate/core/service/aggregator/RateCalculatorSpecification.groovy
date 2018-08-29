package aggregate.core.service.aggregator

import aggregate.core.Constant
import aggregate.core.common.AggregateParameters
import aggregate.core.common.BaseAggregateSpecification
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.TestOutputHeader
import aggregate.core.constant.TestSalesSheetHeader
import spock.lang.Unroll

class RateCalculatorSpecification extends BaseAggregateSpecification {

  RateCalculator calculator

  def setup() {
    calculator = application.getBean(RateCalculator.class)
  }

  @Unroll
  def "#title"() {
    setup:
    // read original csv file
    def original = readOriginalFile(params)
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // aggregate
    def aggregated = calculator.execute(original, TestOutputHeader.PERSONAL_TOTAL_AMOUNT,
        TestSalesSheetHeader.AMOUNT, TestOutputHeader.PERSONAL_AMOUNT_RATE)

    when:
    def result = compare(expected, aggregated)

    then:
    assertCompareResult(result)

    where:
    title                                 | params
    "Calculate Amount Rate for Each Item" | params1
  }

  def getParams1() {
    AggregateParameters.builder()
        .originalHeaders(originalHeaders1)
        .originalFilePath("bin/rate/rateCalculator-original-01.csv")
        .expectedHeaders(expectedHeaders1)
        .expectedFilePath("bin/rate/rateCalculator-result-01.csv")
        .build()
  }

  def getOriginalHeaders1() {
    BaseSheetHeader[] header =
        [
          TestSalesSheetHeader.PERSON,
          TestOutputHeader.PERSONAL_TOTAL_AMOUNT,
          TestSalesSheetHeader.ITEM_CODE,
          TestSalesSheetHeader.AMOUNT
        ]
  }

  def getExpectedHeaders1() {
    BaseSheetHeader[] header =
        [
          TestSalesSheetHeader.PERSON,
          TestOutputHeader.EXPECT_PERSONAL_TOTAL_AMOUNT,
          TestSalesSheetHeader.ITEM_CODE,
          TestOutputHeader.EXPECT_AMOUNT,
          TestOutputHeader.EXPECT_PERSONAL_AMOUNT_RATE
        ]
  }
}
