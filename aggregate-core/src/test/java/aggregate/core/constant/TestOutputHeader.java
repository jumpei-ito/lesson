package aggregate.core.constant;

/**
 * 出力用のヘッダー情報
 */
public enum TestOutputHeader implements BaseSheetHeader {

  /** 月単位の売上の合計ヘッダー */
  MONTHLY_TOTAL_AMONT(0, "売上合計（月）", ColumnType.CURRENCY),
  /** 個人単位の売上の合計ヘッダー */
  PERSONAL_TOTAL_AMONT(1, "売上合計（個人）", ColumnType.CURRENCY),
  /** 曜日ヘッダー */
  DAY_OF_WEEK(2, "曜日", ColumnType.DAY_OF_WEEK),
  /** 月ヘッダー */
  MONTH(3, "月", ColumnType.MONTH),
  /** 個人単位の売上率ヘッダー */
  PERSONAL_AMOUNT_RATE(4, "売上率（個人）", ColumnType.PERCENT),
  /** 個数ヘッダー **/
  COUNT(5, "個数", ColumnType.BIGDECIMAL),
  /** テストケースの実行結果用ヘッダー */
  EXPECT_AMOUNT(6, "受注金額", ColumnType.STRING),
  /** テストケースの実行結果用ヘッダー */
  EXPECT_PERSONAL_TOTAL_AMONT(7, "売上合計（個人）", ColumnType.STRING),
  /** テストケースの実行結果用ヘッダー */
  EXPECT_PERSONAL_AMOUNT_RATE(4, "売上率（個人）", ColumnType.STRING);

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
