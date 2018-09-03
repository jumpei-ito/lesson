package aggregate.core.model.grouping;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.column.Column;

/**
 * Grouping key abstract class for aggregators.
 */
public abstract class GroupingKey {

  private ColumnSet columnSet;
  private BaseSheetHeader key;

  /**
   * Constructor
   *
   * @param columnSet ColumnSet to be aggregated
   * @param key Grouping key
   */
  public GroupingKey(ColumnSet columnSet, BaseSheetHeader key) {
    this.columnSet = columnSet;
    this.key = key;
  }

  /**
   * Getter of columnSet.
   *
   * @return ColumnSet to be aggregated
   */
  public ColumnSet getColumnSet() {
    return columnSet;
  }

  /**
   * Getter of key.
   *
   * @return Grouping key header
   */
  public BaseSheetHeader getKey() {
    return key;
  }

  /**
   * Getter of grouping key column value as String.
   *
   * @return Grouping key column value
   */
  public abstract String getValueAsString();

  @Override
  public abstract int hashCode();

  @Override
  public abstract boolean equals(Object obj);

  /**
   * Returns whether this key can be gotten a grouping value as Column.
   *
   * @return true if this key can be gotten a grouping value as Column, false otherwise.
   */
  public abstract boolean canGetValueAsColumn();

  /**
   * Getter of grouping value as Column.
   *
   * @return Grouping value as Column
   */
  public final Column getValueAsColumn() {
    if (!canGetValueAsColumn()) {
      throw new RuntimeException("Can not get grouping key value as Column class.");
    }
    return columnSet.getColumn(key);
  }

  /**
   * Abstract builder class of {@link GroupingKey}.
   */
  public abstract static class GroupingKeyBuilder {
    /** ColumnSet to be aggregated */
    protected ColumnSet columnSet;
    /** Grouping key header */
    protected BaseSheetHeader key;

    /**
     * Constructor
     */
    public GroupingKeyBuilder() {
      super();
    }

    /**
     * Sets argument ColumnSet and returns this builder.
     *
     * @param columnSet ColumnSet to be aggregated
     * @return GroupingKeyBuilder having argument ColumnSet
     */
    public GroupingKeyBuilder columnSet(ColumnSet columnSet) {
      this.columnSet = columnSet;
      return this;
    }

    /**
     * Sets argument key and returns this builder.
     *
     * @param key Grouping key header
     * @return
     */
    public GroupingKeyBuilder key(BaseSheetHeader key) {
      this.key = key;
      return this;
    }

    /**
     * Returns new {@link GroupingKey} instance.
     *
     * @return New {@link GroupingKey} instance
     */
    public abstract GroupingKey build();
  }

}
