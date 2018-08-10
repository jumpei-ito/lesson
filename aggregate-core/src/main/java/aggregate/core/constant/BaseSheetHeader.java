package aggregate.core.constant;

/**
 * CSV、出力用ヘッダーのインターフェース
 */
public interface BaseSheetHeader {

  /**
   * ヘッダーIDのgetter
   *
   * @return ヘッダーID
   */
  int getId();

  /**
   * ヘッダー名のgetter
   *
   * @return ヘッダー名
   */
  String getHeaderName();

  /**
   * {@link ColumnType}のgetter
   *
   * @return {@link ColumnType}
   */
  ColumnType getColumnType();

  /**
   * 比較メソッド
   *
   * @param header
   * @return ヘッダークラス、ヘッダーIDが同じ場合true
   */
  default boolean equals(BaseSheetHeader header) {
    return this.getClass() == header.getClass() && getId() == header.getId();
  }

}
