package aggregate.core.model.grouping;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.grouping.GroupingKey.GroupingKeyBuilder;

public class GroupingKeysBuilder {

  private List<GroupingKeyBuilder> builders = new ArrayList<>();

  public GroupingKeysBuilder() {
    super();
  }

  public void addHeaderKey(BaseSheetHeader header) {
    builders.add(GroupingHeader.builder().key(header));
  }

  public void addFunctionKey(Function<ColumnSet, ?> function, BaseSheetHeader header) {
    builders.add(GroupingFunction.builder().function(function).key(header));
  }

  public GroupingKeys build(ColumnSet columnSet) {
    return new GroupingKeys(columnSet, builders);
  }

}
