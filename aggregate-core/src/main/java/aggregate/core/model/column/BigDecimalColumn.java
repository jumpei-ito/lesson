package aggregate.core.model.column;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import aggregate.core.util.CastUtils;

/**
 * 数値のカラムの値を保持するクラス<br>
 * 出力用文字列のフォーマットを指定することが可能
 */
public class BigDecimalColumn extends Column {

  /** 出力フォーマット */
  private DecimalFormat outputFormat;
  /** カラム値 */
  private BigDecimal value;

  /**
   * Constructor
   *
   * @param value 数値
   * @param outputFormat 出力フォーマット
   */
  public BigDecimalColumn(BigDecimal value, DecimalFormat outputFormat) {
    super(outputFormat.format(value));
    this.outputFormat = outputFormat;
    this.value = value;
  }

  /**
   * Constructor
   *
   * @param no 列No
   * @param value 数値を表す文字列
   * @param outputFormat 出力フォーマット
   */
  public BigDecimalColumn(int no, String value, DecimalFormat outputFormat) {
    super(no, outputFormat.format(new BigDecimal(value)));
    this.outputFormat = outputFormat;
    this.value = new BigDecimal(value);
  }

  /**
   * Constructor
   *
   * @param no 列No
   * @param value 数値
   * @param outputFormat 出力フォーマット
   */
  public BigDecimalColumn(int no, BigDecimal value, DecimalFormat outputFormat) {
    super(no, outputFormat.format(value));
    this.outputFormat = outputFormat;
    this.value = value;
  }

  /**
   * カラム値のgetter
   *
   * @return カラム値
   */
  public BigDecimal getValue() {
    return value;
  }

  @Override
  public int compareTo(Column column) {
    // TODO: null handling
    return value.subtract(CastUtils.toBigDecimalColumn(column).getValue()).intValue();
  }

  @Override
  public boolean equals(Column column) {
    // TODO: null handling
    return value.equals(CastUtils.toBigDecimalColumn(column).getValue());
  }

  @Override
  public Column clone() {
    return new BigDecimalColumn(getNo(), value, outputFormat);
  }

}
