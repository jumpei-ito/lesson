package aggregate.core.model;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.SortType;

/**
 * Class holds sort information for ColumnSet list.
 */
public class SortKey {

  private BaseSheetHeader key;
  private SortType sortType;
  private boolean isDesc;

  /**
   * Constructor
   *
   * @param key Sort key column header
   * @param sortType Sort type
   */
  public SortKey(BaseSheetHeader key, SortType sortType) {
    this.key = key;
    this.sortType = sortType;
    isDesc = SortType.DESC.equals(sortType);
  }

  /**
   * Getter of key.
   *
   * @return Sort key column header
   */
  public BaseSheetHeader getKey() {
    return key;
  }

  /**
   * Getter of sortType.
   *
   * @return Sort type
   */
  public SortType getSortType() {
    return sortType;
  }

  /**
   * Returns whether sort type is descending order.
   *
   * @return true if sort type is descending order, false otherwise.
   */
  public boolean isDesc() {
    return isDesc;
  }

}
