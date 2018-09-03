package aggregate.core.model.column;

import aggregate.core.constant.Constant;

/**
 * Abstract class for csv column data.
 */
public abstract class Column {

  private int no;
  private String stringValue;

  /**
   * Constructor<br>
   * Assign 0 to column number.
   *
   * @param stringValue Value as String
   */
  public Column(String stringValue) {
    this.no = 0;
    this.stringValue = stringValue;
  }

  /**
   * Constructor
   *
   * @param no Column number
   * @param stringValue Column value as String
   */
  public Column(int no, String stringValue) {
    this.no = no;
    this.stringValue = stringValue;
  }

  /**
   * Compare two {@link Column} values numerically.
   *
   * @param column The Column to be compared
   * @return The value 0 if this Column is equal to the argument Column, other value otherwise.
   */
  public abstract int compareTo(Column column);

  /**
   * Compare two {@link Column} values.
   *
   * @param column The Column to be compared
   * @return true if the Columns are the same, false otherwise.
   */
  public abstract boolean equals(Column column);

  /**
   * Getter of no.
   *
   * @return no Column number
   */
  public int getNo() {
    return no;
  }

  /**
   * Setter of no.
   *
   * @param no Column number
   */
  public void setNo(int no) {
    this.no = no;
  }

  /**
   * Getter of stringValue.
   *
   * @return Column value as String
   */
  public String getValueAsString() {
    return stringValue;
  }

  /**
   * Getter of formatted column value for output.
   *
   * @return Formatted column value for output
   */
  public String getOutputValue() {
    return Constant.DOUBLE_QUOTATION + getValueAsString() + Constant.DOUBLE_QUOTATION;
  }

  /**
   * Creates and returns a copy of this object.
   *
   * return a clone of this instance
   */
  public abstract Column clone();

}
