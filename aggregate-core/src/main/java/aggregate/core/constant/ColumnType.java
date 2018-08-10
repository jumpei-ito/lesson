package aggregate.core.constant;

/**
 * カラム種別
 */
public enum ColumnType {

  /** 文字列 */
  STRING(0),
  /** 数値 */
  BIGDECIMAL(1),
  /** 日付 */
  DATE(2),
  /** 月 */
  MONTH(3),
  /** 曜日 */
  DAY_OF_WEEK(4),
  /** 金額 */
  CURRENCY(5),
  /** 割合 */
  PERCENT(6);

  private int id;

  private ColumnType(int id) {
    this.id = id;
  }

  /**
   * idのgetter
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * {@link ColumnType}との比較
   *
   * @param columnType {@link ColumnType}
   * @return idが同じ場合true
   */
  public boolean equals(ColumnType columnType) {
    return this.id == columnType.getId();
  }

}
