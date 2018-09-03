package aggregate.core.model.grouping;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.column.Column;

/**
 * Grouping key class grouping by header of ColumnSet.
 */
public class GroupingHeader extends GroupingKey {

  /**
   * Constructor
   *
   * @param columnSet ColumnSet to be aggregated
   * @param key Grouping key header
   */
  public GroupingHeader(ColumnSet columnSet, BaseSheetHeader key) {
    super(columnSet, key);
  }

  private Column getKeyColumn() {
    return getColumnSet().getColumn(getKey());
  }

  @Override
  public String getValueAsString() {
    return getColumnSet().getColumn(getKey()).getValueAsString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getKey() == null) ? 0 : getValueAsString().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    GroupingHeader other = (GroupingHeader) obj;
    if (!getKeyColumn().equals(other.getKeyColumn()))
      return false;
    return true;
  }

  @Override
  public boolean canGetValueAsColumn() {
    return true;
  }

  /**
   * Getter of builder class.
   *
   * @return builder of {@link GroupingHeader}
   */
  public static GroupingHeaderBuilder builder() {
    return new GroupingHeaderBuilder();
  }

  /**
   * Builder class of {@link GroupingHeader}.
   */
  public static class GroupingHeaderBuilder extends GroupingKeyBuilder {

    /**
     * Constructor
     */
    public GroupingHeaderBuilder() {
      super();
    }

    @Override
    public GroupingKey build() {
      return new GroupingHeader(super.columnSet, super.key);
    }
  }

}
