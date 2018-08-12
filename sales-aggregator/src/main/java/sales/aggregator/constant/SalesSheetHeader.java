package sales.aggregator.constant;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.ColumnType;

/**
 * 読み込みCSVファイルのヘッダー情報
 */
public enum SalesSheetHeader implements BaseSheetHeader {

  /** 担当者ヘッダー */
  PERSON(0, "担当者", ColumnType.STRING),
  /** 受注日ヘッダー */
  DATE(1, "受注日", ColumnType.DATE),
  /** 受注商品コードヘッダー */
  ITEM_CODE(2, "受注商品コード", ColumnType.STRING),
  /** 受注金額ヘッダー */
  AMOUNT(3, "受注金額", ColumnType.CURRENCY);

  private int id;
  private String headerName;
  private ColumnType columnType;

  private SalesSheetHeader(int id, String headerName, ColumnType columnType) {
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
