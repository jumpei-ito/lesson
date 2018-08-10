package aggregate.core.model.column;

public class StringColumn extends Column {

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
