package aggregate.core.constant;

/**
 * Header settings for original csv files of test code.
 */
public enum TestSalesSheetHeader implements BaseSheetHeader {

  /** Person header */
  PERSON(0, "担当者", ColumnType.STRING),
  /** Date header */
  DATE(1, "受注日", ColumnType.DATE),
  /** Item code header */
  ITEM_CODE(2, "受注商品コード", ColumnType.STRING),
  /** Amount header */
  AMOUNT(3, "受注金額", ColumnType.CURRENCY);

  private int id;
  private String headerName;
  private ColumnType columnType;

  private TestSalesSheetHeader(int id, String headerName, ColumnType columnType) {
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
