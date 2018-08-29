package aggregate.core.service.aggregator

import aggregate.core.Constant
import aggregate.core.common.AggregateParameters
import aggregate.core.common.BaseAggregateSpecification
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.SortType
import aggregate.core.constant.TestOutputHeader
import aggregate.core.constant.TestSalesSheetHeader
import aggregate.core.model.SortKey
import aggregate.core.model.grouping.GroupingKeysBuilder
import spock.lang.Unroll

class CounterSpecification extends BaseAggregateSpecification {

  Counter counter

  def setup() {
    counter = application.getBean(Counter.class)
  }

  @Unroll
  def "Count #title"() {
    setup:
    // read original csv file
    def original = readOriginalFile(params)
    // read expected csv file
    def expected = readExpectedFile(params)
    // aggregate
    def aggregated = counter.execute(original, params.groupingKeyBuilder,
        params.aggregateKeyHeader, params.sortKeys)

    when:
    def result = compare(expected, aggregated)

    then:
    assertCompareResult(result)

    where:
    title                  | params
    "by Two Grouping Keys" | params1
    "by Third Header"      | params2
  }

  def getParams1() {
    AggregateParameters.builder()
        .expectedHeaders(expectedHeaders1)
        .expectedFilePath("bin/counter/counter-result-01.csv")
        .groupingKeyBuilder(Constant.BUILDER_GROUING_BY_PERSON_AND_ITEM)
        .aggregateKeyHeader(TestOutputHeader.COUNT)
        .sortKeys(Constant.SORT_KEY_BY_PERSON_AND_ITEM).build()
  }

  def getExpectedHeaders1() {
    BaseSheetHeader[] header =
        [TestSalesSheetHeader.PERSON, TestSalesSheetHeader.ITEM_CODE, TestOutputHeader.COUNT]
  }

  def getParams2() {
    AggregateParameters.builder()
        .expectedHeaders(expectedHeaders2)
        .expectedFilePath("bin/counter/counter-result-02.csv")
        .groupingKeyBuilder(groupingKeyBuilder2)
        .aggregateKeyHeader(TestOutputHeader.COUNT)
        .sortKeys(sortKeys2).build()
  }

  def getExpectedHeaders2() {
    BaseSheetHeader[] header = [TestSalesSheetHeader.ITEM_CODE, TestOutputHeader.COUNT]
  }

  def getGroupingKeyBuilder2() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder()
    builder.addHeaderKey(TestSalesSheetHeader.ITEM_CODE)
    return builder
  }

  def getSortKeys2() {
    Arrays.asList(new SortKey(TestSalesSheetHeader.ITEM_CODE, SortType.ASC))
  }
}
