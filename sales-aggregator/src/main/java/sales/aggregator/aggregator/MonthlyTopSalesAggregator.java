package sales.aggregator.aggregator;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.SortType;
import aggregate.core.model.AggregateKey;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.SortKey;
import aggregate.core.model.grouping.GroupingKeysBuilder;
import aggregate.core.service.BaseAggregator;
import aggregate.core.service.ColumnSetConverter;
import aggregate.core.service.aggregator.MaxPicker;
import aggregate.core.service.aggregator.RateCalculator;
import aggregate.core.service.aggregator.Summarizer;
import aggregate.core.util.FunctionUtils;
import sales.aggregator.constant.OutputHeader;
import sales.aggregator.constant.SalesSheetHeader;

@Component
public class MonthlyTopSalesAggregator implements BaseAggregator {

  @Autowired
  private Summarizer summarizer;
  @Autowired
  private MaxPicker maxPicker;
  @Autowired
  private RateCalculator rateCalculator;
  @Autowired
  private ColumnSetConverter converter;

  @Override
  public int getExecuteOrder() {
    return 200;
  }

  @Override
  public List<ColumnSet> aggregate(List<ColumnSet> columnSets) {
    // Aggregate amount each month and person.
    List<ColumnSet> summaryPerMonthAndPerson = getAmountSummaryByMonthAndPerson(columnSets);
    // Aggregate amount each month.
    List<ColumnSet> monthlySummary = getAmountSummaryPerMonth(summaryPerMonthAndPerson);
    // Get person of max amount.
    List<ColumnSet> monthlyBestPersons = getMonthlyMaxAmount(summaryPerMonthAndPerson);
    // Merge two aggregate results.
    List<ColumnSet> convertedResult = converter.mergeToRight(monthlySummary, monthlyBestPersons);
    // Aggregate personal amount rate.
    return getPersonalAmountRate(convertedResult);
  }

  private List<ColumnSet> getAmountSummaryByMonthAndPerson(List<ColumnSet> columnSets) {
    return summarizer.execute(columnSets, getGroupingKeysByMonthAndPerson(),
        getSummaryKeyOfAmount(), getSortKeysByMonthAndTotalByPerson());
  }

  private List<ColumnSet> getAmountSummaryPerMonth(List<ColumnSet> columnSets) {
    return summarizer.execute(columnSets, getGroupingKeysByMonth(),
        getAggregateKeyForMonthlySummary(), getSortKeyByMonth());
  }

  private List<ColumnSet> getMonthlyMaxAmount(List<ColumnSet> columnSets) {
    return maxPicker.execute(columnSets, getGroupingKeysByMonth(),
        OutputHeader.PERSONAL_TOTAL_AMONT, getSortKeyByMonth(), getOutputHeaders());
  }

  private List<ColumnSet> getPersonalAmountRate(List<ColumnSet> columnSets) {
    return rateCalculator.execute(columnSets, OutputHeader.MONTHLY_TOTAL_AMONT,
        OutputHeader.PERSONAL_TOTAL_AMONT, OutputHeader.PERSONAL_AMOUNT_RATE);
  }

  private GroupingKeysBuilder getGroupingKeysByMonth() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addHeaderKey(OutputHeader.MONTH);
    return builder;
  }

  private GroupingKeysBuilder getGroupingKeysByMonthAndPerson() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    // first grouping key
    builder.addFunctionKey(FunctionUtils.getMonthValue(SalesSheetHeader.DATE),
        OutputHeader.MONTH);
    // second grouping key
    builder.addHeaderKey(SalesSheetHeader.PERSON);
    return builder;
  }

  private AggregateKey getAggregateKeyForMonthlySummary() {
    return new AggregateKey(OutputHeader.PERSONAL_TOTAL_AMONT, OutputHeader.MONTHLY_TOTAL_AMONT);
  }

  private AggregateKey getSummaryKeyOfAmount() {
    return new AggregateKey(SalesSheetHeader.AMOUNT, OutputHeader.PERSONAL_TOTAL_AMONT);
  }

  private List<SortKey> getSortKeysByMonthAndTotalByPerson() {
    return Arrays.asList(new SortKey(OutputHeader.MONTH, SortType.ASC),
        new SortKey(OutputHeader.PERSONAL_TOTAL_AMONT, SortType.DESC));
  }

  private List<SortKey> getSortKeyByMonth() {
    return Arrays.asList(new SortKey(OutputHeader.MONTH, SortType.ASC));
  }

  private List<BaseSheetHeader> getOutputHeaders() {
    return Arrays.asList(OutputHeader.MONTH, SalesSheetHeader.PERSON,
        OutputHeader.PERSONAL_TOTAL_AMONT);
  }

}
