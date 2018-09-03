package aggregate.core.model.column;

/**
 * Column class for String value.
 */
public class StringColumn extends Column {

  /**
   * Constructor
   *
   * @param no Column number
   * @param value Column value
   */
  public StringColumn(int no, String value) {
    super(no, value);
  }

  @Override
  public int compareTo(Column column) {
    // TODO: null handling
    return getValueAsString().compareTo(column.getValueAsString());
  }

  @Override
  public boolean equals(Column column) {
    // TODO: null handling
    return getValueAsString().equals(column.getValueAsString());
  }

  public String getValue() {
    return getValueAsString();
  }

  @Override
  public Column clone() {
    return new StringColumn(getNo(), getValueAsString());
  }

}
