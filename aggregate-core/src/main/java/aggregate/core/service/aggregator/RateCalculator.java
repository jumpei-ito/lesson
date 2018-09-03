package aggregate.core.service.aggregator;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.Constant;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.column.BigDecimalColumn;
import aggregate.core.model.column.Column;
import aggregate.core.util.FunctionUtils;

/**
 * Aggregator class to calculate a rate from two columns.
 */
@Component
public class RateCalculator {

  /**
   * Calcurates a rate from argument columns and add calcurated result column to right of ColumnSet.
   *
   * @param columnSets List of ColumnSet to be aggregated
   * @param dividendHeader Dividend column header
   * @param divisorHeader Divisor column header
   * @param outputHeader Calcurated result column header
   * @return Calcurated ColumnSet list
   */
  public List<ColumnSet> execute(List<ColumnSet> columnSets, BaseSheetHeader dividendHeader,
      BaseSheetHeader divisorHeader, BaseSheetHeader outputHeader) {
    // calculate
    columnSets.forEach(columnSet -> columnSet.addColumn(outputHeader,
        getPercentageColumn(FunctionUtils.getBigDecimalValue(dividendHeader).apply(columnSet),
            FunctionUtils.getBigDecimalValue(divisorHeader).apply(columnSet), outputHeader)));
    return columnSets;
  }

  private Column getPercentageColumn(BigDecimal divisor, BigDecimal dividend,
      BaseSheetHeader outputHeader) {
    return new BigDecimalColumn(dividend.divide(divisor, 3, BigDecimal.ROUND_HALF_UP),
        Constant.PERCENT_FORMAT);
  }

}
