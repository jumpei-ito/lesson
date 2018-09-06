package sales.aggregator

import aggregate.core.AggregateApplication
import aggregate.core.common.BaseParameters
import aggregate.core.common.BaseParametersBuilder
import aggregate.core.constant.BaseSheetHeader
import aggregate.core.constant.Constant
import aggregate.core.service.BaseAggregator

class SalesAggregateParameters extends BaseParameters {
  /**  */
  BaseAggregator aggregator

  static def builder() {
    new SalesAggregateParametersBuilder()
  }

  static class SalesAggregateParametersBuilder extends BaseParametersBuilder {
    /**  */
    BaseAggregator aggregator

    def aggregator(BaseAggregator aggregator) {
      this.aggregator = aggregator
      return this
    }

    def application(AggregateApplication application) {
      originalHeaders = application.getBean(BaseSheetHeader[].class)
      originalFilePath = application.getProperty(Constant.P_ORIGINAL_CSV_PATH)
      return this
    }

    @Override
    def build() {
      new SalesAggregateParameters(
          originalHeaders: this.originalHeaders,
          originalFilePath: this.originalFilePath,
          expectedHeaders: this.expectedHeaders,
          expectedFilePath: this.expectedFilePath,
          aggregator: this.aggregator)
    }
  }
}
