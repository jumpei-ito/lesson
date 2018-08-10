package aggregate.core.model.column;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import aggregate.core.util.CastUtils;

/**
 * 日付のカラムの値を保持するクラス
 */
public class DateColumn extends Column {

  /** 日付フォーマット */
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
  /** カラム値 */
  private LocalDate value;

  /**
   * Constructor
   *
   * @param no 列No
   * @param value 日付を表す文字列（yyyy/MM/dd）
   */
  public DateColumn(int no, String value) {
    super(no, value);
    this.value = LocalDate.parse(value, formatter);
  }

  /**
   * Constructor
   *
   * @param no 列No
   * @param value 日付
   */
  public DateColumn(int no, LocalDate value) {
    super(no, value.format(formatter));
    this.value = value;
  }

  /**
   * カラム値のgetter
   *
   * @return カラム値
   */
  public LocalDate getValue() {
    return value;
  }

  @Override
  public int compareTo(Column column) {
    // TODO: null handling
    return value.compareTo(CastUtils.toDateColumn(column).getValue());
  }

  @Override
  public boolean equals(Column column) {
    // TODO: null handling
    return value.equals(CastUtils.toDateColumn(column).getValue());
  }

  @Override
  public Column clone() {
    return new DateColumn(getNo(), value);
  }

}
