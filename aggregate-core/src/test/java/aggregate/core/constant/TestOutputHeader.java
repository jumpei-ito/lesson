package aggregate.core.constant;

/**
 * Header settings for output csv files of test code.
 */
public enum TestOutputHeader implements BaseSheetHeader {

  /** Monthly total amount header */
  MONTHLY_TOTAL_AMOUNT(0, "売上合計（月）", ColumnType.CURRENCY),
  /** Personal total amount header */
  PERSONAL_TOTAL_AMOUNT(1, "売上合計（個人）", ColumnType.CURRENCY),
  /** Day of week header */
  DAY_OF_WEEK(2, "曜日", ColumnType.DAY_OF_WEEK),
  /** Month header */
  MONTH(3, "月", ColumnType.MONTH),
  /** Personal amount rate header */
  PERSONAL_AMOUNT_RATE(4, "売上率（個人）", ColumnType.PERCENT),
  /** Count header **/
  COUNT(5, "受注数", ColumnType.BIGDECIMAL),
  /** Amount header expected by test code */
  EXPECT_AMOUNT(6, "受注金額", ColumnType.STRING),
  /** Personal total amount header expected by test code */
  EXPECT_PERSONAL_TOTAL_AMOUNT(7, "売上合計（個人）", ColumnType.STRING),
  /** Personal amount rate header expected by test code */
  EXPECT_PERSONAL_AMOUNT_RATE(8, "売上率（個人）", ColumnType.STRING),
  /** Month header expected by test code */
  EXPECT_MONTH(9, "月", ColumnType.STRING),
  /** Monthly total amount header expected by test code */
  EXPECT_MONTHLY_TOTAL_AMOUNT(10, "売上合計（月）", ColumnType.STRING),
  /** Day of week header expected by test code */
  EXPECT_DAY_OF_WEEK(11, "曜日", ColumnType.STRING);

  private int id;
  private String headerName;
  private ColumnType columnType;

  private TestOutputHeader(int id, String headerName, ColumnType columnType) {
    this.id = id;
    this.headerName = headerName;
    this.columnType = columnType;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getHeaderName() {
    return headerName;
  }

  @Override
  public ColumnType getColumnType() {
    return columnType;
  }
}
