package aggregate.core.model;

import aggregate.core.constant.BaseSheetHeader;

public class AggregateKey {

  private BaseSheetHeader aggregateKey;
  private BaseSheetHeader outputKey;

  public AggregateKey(BaseSheetHeader aggregateKey) {
    this.aggregateKey = aggregateKey;
    this.outputKey = aggregateKey;
  }

  public AggregateKey(BaseSheetHeader aggregateKey, BaseSheetHeader outputKey) {
    this.aggregateKey = aggregateKey;
    this.outputKey = outputKey;
  }

  public BaseSheetHeader getAggregateKey() {
    return aggregateKey;
  }

  public BaseSheetHeader getOutputKey() {
    return outputKey;
  }

}
