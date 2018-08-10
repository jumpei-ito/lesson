package aggregate.core.model.grouping;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.column.Column;

public abstract class GroupingKey {

  private ColumnSet columnSet;
  private BaseSheetHeader key;

  public GroupingKey(ColumnSet columnSet, BaseSheetHeader key) {
    this.columnSet = columnSet;
    this.key = key;
  }

  public ColumnSet getColumnSet() {
    return columnSet;
  }

  public BaseSheetHeader getKey() {
    return key;
  }

  public abstract String getValueAsString();

  @Override
  public abstract int hashCode();

  @Override
  public abstract boolean equals(Object obj);

  public abstract boolean canGetValueAsColumn();

  public final Column getValueAsColumn() {
    if (!canGetValueAsColumn()) {
      throw new RuntimeException("Can not get grouping key value as Column class.");
    }
    return columnSet.getColumn(key);
  }

  public abstract static class GroupingKeyBuilder {

    protected ColumnSet columnSet;
    protected BaseSheetHeader key;

    public GroupingKeyBuilder() {
      super();
    }

    public GroupingKeyBuilder columnSet(ColumnSet columnSet) {
      this.columnSet = columnSet;
      return this;
    }

    public GroupingKeyBuilder key(BaseSheetHeader key) {
      this.key = key;
      return this;
    }

    public abstract GroupingKey build();
  }

}
