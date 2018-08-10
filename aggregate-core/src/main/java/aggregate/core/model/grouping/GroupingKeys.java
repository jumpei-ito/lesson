package aggregate.core.model.grouping;

import java.util.List;
import java.util.stream.Collectors;

import aggregate.core.model.ColumnSet;
import aggregate.core.model.grouping.GroupingKey.GroupingKeyBuilder;

public class GroupingKeys {

  private List<GroupingKey> groupingKeys;

  public GroupingKeys(List<GroupingKey> groupingKeys) {
    this.groupingKeys = groupingKeys;
  }

  public GroupingKeys(ColumnSet columnSet, List<GroupingKeyBuilder> builders) {
    this.groupingKeys = builders.stream().map(builder -> builder.columnSet(columnSet).build())
        .collect(Collectors.toList());
  }

  public List<GroupingKey> getKeys() {
    return groupingKeys;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((groupingKeys == null) ? 0
        : groupingKeys.stream().mapToInt(key -> key.hashCode()).sum());
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
    GroupingKeys other = (GroupingKeys) obj;
    if (groupingKeys == null) {
      if (other.groupingKeys != null)
        return false;
    } else if (!groupingKeys.equals(other.groupingKeys))
      return false;
    for (int i = 0; i < groupingKeys.size(); i++) {
      if (!groupingKeys.get(i).equals(other.groupingKeys.get(i))) {
        return false;
      }
    }
    return true;
  }

}
