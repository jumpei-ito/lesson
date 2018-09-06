package sales.aggregator

import aggregate.core.common.BaseAggregateSpecification
import aggregate.core.common.Constant
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.TestOutputHeader
import sales.aggregator.aggregator.DayOfWeekAmountSummaryAggregator
import sales.aggregator.aggregator.ItemCountAggregator
import sales.aggregator.aggregator.MonthlyRankingAggregator
import sales.aggregator.aggregator.MonthlyTopSalesAggregator
import sales.aggregator.aggregator.PersonAmountSummaryAggregator
import sales.aggregator.constant.OutputHeader
import sales.aggregator.constant.SalesSheetHeader
import spock.lang.Unroll

class SalesAggregateSpecification extends BaseAggregateSpecification {

  @Unroll
  def "#title Test"() {
    setup:
    // read original csv file
    def original = readOriginalFile(params)
    // read expected csv file
    def expected = readExpectedFile(params, Constant.QUOTE)
    // aggregate
    def aggregated = params.aggregator.aggregate(original)

    when:
    def result = compare(expected, aggregated)

    then:
    assertCompareResult(result)

    where:
    title                               | params
    "Summarize Amount Each Day of Week" | dayOfWeekSummayParams
    "Count Item Each Person"            | itemCountParams
    "Aggregate Monthly Amount Ranking"  | monthlyRankingParams
    "Aggregate Monthly Top Sales"       | monthlyTopSalesParams
    "Aggregate Personal Amount Summary" | personSummaryParams
  }

  def getDayOfWeekSummayParams() {
    SalesAggregateParameters.builder()
        .application(application)
        .expectedHeaders(dayOfWeekSummayExpectedHeaders)
        .expectedFilePath("bin/expected/dayOfWeekSummary-result.csv")
        .aggregator(application.getBean(DayOfWeekAmountSummaryAggregator.class))
        .build()
  }

  def getDayOfWeekSummayExpectedHeaders() {
    BaseSheetHeader[] header =
        [TestOutputHeader.EXPECT_DAY_OF_WEEK, TestOutputHeader.EXPECT_AMOUNT]
  }

  def getItemCountParams() {
    SalesAggregateParameters.builder()
        .application(application)
        .expectedHeaders(itemCountExpectedHeaders)
        .expectedFilePath("bin/expected/itemCount-result.csv")
        .aggregator(application.getBean(ItemCountAggregator.class))
        .build()
  }

  def getItemCountExpectedHeaders() {
    BaseSheetHeader[] header =
        [SalesSheetHeader.PERSON, SalesSheetHeader.ITEM_CODE, OutputHeader.COUNT]
  }

  def getMonthlyRankingParams() {
    SalesAggregateParameters.builder()
        .application(application)
        .expectedHeaders(monthlyRankingExpectedHeaders)
        .expectedFilePath("bin/expected/monthlyRanking-result.csv")
        .aggregator(application.getBean(MonthlyRankingAggregator.class))
        .build()
  }

  def getMonthlyRankingExpectedHeaders() {
    BaseSheetHeader[] header =
        [TestOutputHeader.EXPECT_MONTH, SalesSheetHeader.PERSON, TestOutputHeader.EXPECT_PERSONAL_TOTAL_AMOUNT]
  }

  def getMonthlyTopSalesParams() {
    SalesAggregateParameters.builder()
        .application(application)
        .expectedHeaders(monthlyTopSalesExpectedHeaders)
        .expectedFilePath("bin/expected/monthlyTopSales-result.csv")
        .aggregator(application.getBean(MonthlyTopSalesAggregator.class))
        .build()
  }

  def getMonthlyTopSalesExpectedHeaders() {
    BaseSheetHeader[] header =
        [
          TestOutputHeader.EXPECT_MONTH,
          TestOutputHeader.EXPECT_MONTHLY_TOTAL_AMOUNT,
          SalesSheetHeader.PERSON,
          TestOutputHeader.EXPECT_PERSONAL_TOTAL_AMOUNT,
          TestOutputHeader.EXPECT_PERSONAL_AMOUNT_RATE
        ]
  }

  def getPersonSummaryParams() {
    SalesAggregateParameters.builder()
        .application(application)
        .expectedHeaders(personSummaryExpectedHeaders)
        .expectedFilePath("bin/expected/personSummary-result.csv")
        .aggregator(application.getBean(PersonAmountSummaryAggregator.class))
        .build()
  }

  def getPersonSummaryExpectedHeaders() {
    BaseSheetHeader[] header =
        [SalesSheetHeader.PERSON, SalesSheetHeader.ITEM_CODE, TestOutputHeader.EXPECT_AMOUNT]
  }
}

