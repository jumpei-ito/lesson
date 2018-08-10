package aggregate.core.constant;

/**
 * ソート種別
 */
public enum SortType {

  /** 照準 */
  ASC(0),
  /** 降順 */
  DESC(1);

  private int id;

  private SortType(int id) {
    this.id = id;
  }

  /**
   * idのgetter
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * {@link SortType}との比較
   *
   * @param sortType {@link SortType}
   * @return idが同じ場合true
   */
  public boolean equals(SortType sortType) {
    return id == sortType.getId();
  }

}
