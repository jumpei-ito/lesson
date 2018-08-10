package aggregate.core.model.grouping;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.column.Column;

public class GroupingHeader extends GroupingKey {

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

  public static GroupingHeaderBuilder builder() {
    return new GroupingHeaderBuilder();
  }

  public static class GroupingHeaderBuilder extends GroupingKeyBuilder {

    public GroupingHeaderBuilder() {
      super();
    }

    @Override
    public GroupingKey build() {
      return new GroupingHeader(super.columnSet, super.key);
    }
  }

}
