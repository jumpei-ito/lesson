package aggregate.core.constant;

/**
 * Type of columns.
 */
public enum ColumnType {

  /** String */
  STRING(0),
  /** Number */
  BIGDECIMAL(1),
  /** Date */
  DATE(2),
  /** Month */
  MONTH(3),
  /** Day of week */
  DAY_OF_WEEK(4),
  /** Currency (JPY) */
  CURRENCY(5),
  /** Percent, Rate */
  PERCENT(6);

  private int id;

  private ColumnType(int id) {
    this.id = id;
  }

  /**
   * Getter of id
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Compare two {@link ColumnType} values.
   *
   * @param columnType The ColumnType to be compared
   * @return true if the ColumnTypes are the same, false otherwise.
   */
  public boolean equals(ColumnType columnType) {
    return this.id == columnType.getId();
  }

}
