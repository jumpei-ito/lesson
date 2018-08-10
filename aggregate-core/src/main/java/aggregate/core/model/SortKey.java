package aggregate.core.model;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.SortType;

public class SortKey {

  private BaseSheetHeader key;
  private SortType sortType;
  private boolean isDesc;

  public SortKey(BaseSheetHeader key, SortType sortType) {
    this.key = key;
    this.sortType = sortType;
    isDesc = SortType.DESC.equals(sortType);
  }

  public BaseSheetHeader getKey() {
    return key;
  }

  public SortType getSortType() {
    return sortType;
  }

  public boolean isDesc() {
    return isDesc;
  }

}
