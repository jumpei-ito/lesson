package aggregate.core.constant;

/**
 * Interface of header for csv files.
 */
public interface BaseSheetHeader {

  /**
   * Getter of header id.
   *
   * @return Header id
   */
  int getId();

  /**
   * Getter of header name.
   *
   * @return Header name
   */
  String getHeaderName();

  /**
   * Getter of {@link ColumnType}.
   *
   * @return {@link ColumnType}
   */
  ColumnType getColumnType();

  /**
   * Compare two {@link BaseSheetHeader} values.
   *
   * @param header The BaseSheetHeader to be compared
   * @return true if the BaseSheetHeaders are the same, false otherwise.
   */
  default boolean equals(BaseSheetHeader header) {
    return this.getClass() == header.getClass() && getId() == header.getId();
  }

}
