package aggregate.core.exception;

import aggregate.core.constant.ColumnType;

public class MissingColumnTypeException extends RuntimeException {

  private ColumnType expected;
  private ColumnType actual;

  public MissingColumnTypeException(ColumnType expected, ColumnType actual) {
    super(String.format("Target column type mast be %s. Actual -> %s", expected.toString(),
        actual.toString()));
    this.expected = expected;
    this.actual = actual;
  }

  public ColumnType getExpected() {
    return expected;
  }

  public ColumnType getActual() {
    return actual;
  }

}
