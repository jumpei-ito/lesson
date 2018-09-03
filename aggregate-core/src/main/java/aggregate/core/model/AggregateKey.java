package aggregate.core.model;

import aggregate.core.constant.BaseSheetHeader;

/**
 * Class holding headers to be aggregated.
 */
public class AggregateKey {

  private BaseSheetHeader aggregateKey;
  private BaseSheetHeader outputKey;

  /**
   * Constructor
   *
   * @param aggregateKey Header to be aggregated
   */
  public AggregateKey(BaseSheetHeader aggregateKey) {
    this.aggregateKey = aggregateKey;
    this.outputKey = aggregateKey;
  }

  /**
   * Constructor
   *
   * @param aggregateKey Header to be aggregated
   * @param outputKey Header of aggregate result
   */
  public AggregateKey(BaseSheetHeader aggregateKey, BaseSheetHeader outputKey) {
    this.aggregateKey = aggregateKey;
    this.outputKey = outputKey;
  }

  /**
   * Getter of aggregateKey.
   *
   * @return Header to be aggregated
   */
  public BaseSheetHeader getAggregateKey() {
    return aggregateKey;
  }

  /**
   * Getter of outputKey.
   *
   * @return Header of aggregate result
   */
  public BaseSheetHeader getOutputKey() {
    return outputKey;
  }

}
