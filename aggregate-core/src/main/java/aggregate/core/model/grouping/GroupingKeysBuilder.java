package aggregate.core.model.grouping;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.model.ColumnSet;
import aggregate.core.model.column.Column;
import aggregate.core.model.grouping.GroupingKey.GroupingKeyBuilder;

/**
 * Builder class of {@link GroupingKeys}.
 */
public class GroupingKeysBuilder {

  private List<GroupingKeyBuilder> builders = new ArrayList<>();

  /**
   * Constructor
   */
  public GroupingKeysBuilder() {
    super();
  }

  /**
   * Adds argument grouping key as header.
   *
   * @param header Grouping key header
   */
  public void addHeaderKey(BaseSheetHeader header) {
    builders.add(GroupingHeader.builder().key(header));
  }

  /**
   * Adds argument grouping key as function.
   *
   * @param function Function getting value from {@link Column}
   * @param header Grouping key header
   */
  public void addFunctionKey(Function<ColumnSet, ?> function, BaseSheetHeader header) {
    builders.add(GroupingFunction.builder().function(function).key(header));
  }

  /**
   * Returns new {@link GroupingKeys} instance.
   *
   * @param columnSet ColumnSet to be aggregated
   * @return New {@link GroupingKeys} instance
   */
  public GroupingKeys build(ColumnSet columnSet) {
    return new GroupingKeys(columnSet, builders);
  }

}
