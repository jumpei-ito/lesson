package aggregate.core.model.column;

import aggregate.core.constant.Constant;

/**
 * カラムの値を保持する抽象クラス
 */
public abstract class Column {

  private int no;
  private String stringValue;

  /**
   * Constructor<br>
   * 列Noは0とする
   *
   * @param stringValue 値を表す文字列
   */
  public Column(String stringValue) {
    this.no = 0;
    this.stringValue = stringValue;
  }

  /**
   * Constructor
   *
   * @param no 列No
   * @param stringValue 値を表す文字列
   */
  public Column(int no, String stringValue) {
    this.no = no;
    this.stringValue = stringValue;
  }

  /**
   * {@link Column}のインスタンスと内容を比較する<br>
   *
   * @param column
   * @return 内容が同じ場合：0, 内容が異なる場合：差分
   */
  public abstract int compareTo(Column column);

  /**
   * {@link Column}のインスタンスと内容を比較する<br>
   *
   * @param column {@link Column}
   * @return 内容が同じ場合true
   */
  public abstract boolean equals(Column column);

  /**
   * 列Noのgetter
   *
   * @return no 列No
   */
  public int getNo() {
    return no;
  }

  /**
   * 列Noのsetter
   *
   * @param no 列No
   */
  public void setNo(int no) {
    this.no = no;
  }

  /**
   * 値を表す文字列を取得する
   *
   * @return 値を表す文字列
   */
  public String getValueAsString() {
    return stringValue;
  }

  /**
   * 出力用の値を表す文字列を取得する
   *
   * @return 出力用の値を表す文字列
   */
  public String getOutputValue() {
    return Constant.DOUBLE_QUOTATION + getValueAsString() + Constant.DOUBLE_QUOTATION;
  }

  /**
   * {@link Column}を複製する
   *
   * return {@link Column}
   */
  public abstract Column clone();

}
