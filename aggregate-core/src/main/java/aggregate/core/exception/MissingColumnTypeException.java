package aggregate.core.exception;

import aggregate.core.constant.ColumnType;

/**
 * Exception class for illegal {@link ColumnType}.
 */
public class MissingColumnTypeException extends RuntimeException {

  private ColumnType expected;
  private ColumnType actual;

  /**
   * Conctructor
   *
   * @param expected Expected {@link ColumnType}
   * @param actual Actual {@link ColumnType}
   */
  public MissingColumnTypeException(ColumnType expected, ColumnType actual) {
    super(String.format("Target column type mast be %s. Actual -> %s", expected.toString(),
        actual.toString()));
    this.expected = expected;
    this.actual = actual;
  }

  /**
   * Getter of expected {@link ColumnType}.
   *
   * @return Expected {@link ColumnType}
   */
  public ColumnType getExpected() {
    return expected;
  }

  /**
   * Getter of actual {@link ColumnType}.
   *
   * @return Actual {@link ColumnType}
   */
  public ColumnType getActual() {
    return actual;
  }

}
