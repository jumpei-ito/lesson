package aggregate.core.model.column;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import aggregate.core.util.CastUtils;

/**
 * Column Class for number value.<br>
 * Allowed to assign number format of output.
 */
public class BigDecimalColumn extends Column {

  private DecimalFormat outputFormat;
  private BigDecimal value;

  /**
   * Constructor
   *
   * @param value Column value of number
   * @param outputFormat Format for output
   */
  public BigDecimalColumn(BigDecimal value, DecimalFormat outputFormat) {
    super(outputFormat.format(value));
    this.outputFormat = outputFormat;
    this.value = value;
  }

  /**
   * Constructor
   *
   * @param no Column number
   * @param value Column value of number as String
   * @param outputFormat Format for output
   */
  public BigDecimalColumn(int no, String value, DecimalFormat outputFormat) {
    super(no, outputFormat.format(new BigDecimal(value)));
    this.outputFormat = outputFormat;
    this.value = new BigDecimal(value);
  }

  /**
   * Constructor
   *
   * @param no Column number
   * @param value Column value of number
   * @param outputFormat Format for output
   */
  public BigDecimalColumn(int no, BigDecimal value, DecimalFormat outputFormat) {
    super(no, outputFormat.format(value));
    this.outputFormat = outputFormat;
    this.value = value;
  }

  /**
   * Getter of value.
   *
   * @return Column value
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
