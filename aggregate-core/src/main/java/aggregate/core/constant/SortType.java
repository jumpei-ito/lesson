package aggregate.core.constant;

/**
 * Type of sort.
 */
public enum SortType {

  /** Ascending order */
  ASC(0),
  /** descending order */
  DESC(1);

  private int id;

  private SortType(int id) {
    this.id = id;
  }

  /**
   * Getter of id.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Compare two {@link SortType} values.
   *
   * @param sortType The SortType to be compared
   * @return true if the SortTypes are the same, false otherwise.
   */
  public boolean equals(SortType sortType) {
    return id == sortType.getId();
  }

}
