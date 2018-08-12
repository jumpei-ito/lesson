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

@Component
public class RateCalculator {

  /**
   * 引数のヘッダーを基に割合を算出し、{@link ColumnSet}の１番右のカラムに追加する
   *
   * @param columnSets カラムセット
   * @param divisorHeader 割る数を保持するヘッダー
   * @param dividendHeader 割られる数を保持するヘッダー
   * @param outputHeader 計算結果ヘッダー
   * @return
   */
  public List<ColumnSet> execute(List<ColumnSet> columnSets, BaseSheetHeader divisorHeader,
      BaseSheetHeader dividendHeader, BaseSheetHeader outputHeader) {
    columnSets.forEach(columnSet -> columnSet.addColumn(outputHeader,
        getPercentageColumn(FunctionUtils.getBigDecimalValue(divisorHeader).apply(columnSet),
            FunctionUtils.getBigDecimalValue(dividendHeader).apply(columnSet), outputHeader)));
    return columnSets;
  }

  private Column getPercentageColumn(BigDecimal divisor, BigDecimal dividend,
      BaseSheetHeader outputHeader) {
    return new BigDecimalColumn(dividend.divide(divisor, 3, BigDecimal.ROUND_HALF_UP),
        Constant.PERCENT_FORMAT);
  }

}
