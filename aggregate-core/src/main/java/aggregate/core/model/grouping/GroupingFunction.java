package aggregate.core.model.grouping;

import java.util.function.Function;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;

public class GroupingFunction extends GroupingKey {

  private Function<ColumnSet, ?> function;

  public GroupingFunction(ColumnSet columnSet, BaseSheetHeader key,
      Function<ColumnSet, ?> function) {
    super(columnSet, key);
    this.function = function;
  }

  public Function<ColumnSet, ?> getFunction() {
    return function;
  }

  @Override
  public String getValueAsString() {
    return function.apply(getColumnSet()).toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((function == null) ? 0 : getValueAsString().hashCode());
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
    GroupingFunction other = (GroupingFunction) obj;
    if (!getValueAsString().equals(other.getValueAsString()))
      return false;
    return true;
  }

  @Override
  public boolean canGetValueAsColumn() {
    return false;
  }

  public static GroupingFunctionBuilder builder() {
    return new GroupingFunctionBuilder();
  }

  public static class GroupingFunctionBuilder extends GroupingKeyBuilder {

    private Function<ColumnSet, ?> function;

    public GroupingFunctionBuilder() {
      super();
    }

    public GroupingFunctionBuilder function(Function<ColumnSet, ?> function) {
      this.function = function;
      return this;
    }

    @Override
    public GroupingKey build() {
      return new GroupingFunction(super.columnSet, super.key, function);
    }
  }

}
