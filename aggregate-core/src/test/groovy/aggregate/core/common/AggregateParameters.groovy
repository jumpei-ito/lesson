package aggregate.core.common

import aggregate.core.constant.BaseSheetHeader
import aggregate.core.model.AggregateKey
import aggregate.core.model.SortKey
import aggregate.core.model.grouping.GroupingKeysBuilder

class AggregateParameters extends BaseParameters {
  /**  */
  GroupingKeysBuilder groupingKeyBuilder
  /**  */
  AggregateKey aggregateKey
  /**  */
  BaseSheetHeader aggregateKeyHeader
  /**  */
  List<SortKey> sortKeys
  /**  */
  List<BaseSheetHeader> outputHeaders

  static def builder() {
    new AggregateParametersBuilder()
  }

  static class AggregateParametersBuilder extends BaseParametersBuilder {
    GroupingKeysBuilder groupingKeyBuilder
    AggregateKey aggregateKey
    BaseSheetHeader aggregateKeyHeader
    List<SortKey> sortKeys
    List<BaseSheetHeader> outputHeaders

    AggregateParametersBuilder() {
      // set default value
      originalHeaders = Constant.ORIGINAL_HEADERS
      originalFilePath = Constant.ORIGINAL_FILE_PATH
    }

    def groupingKeyBuilder(GroupingKeysBuilder groupingKeyBuilder) {
      this.groupingKeyBuilder = groupingKeyBuilder
      return this
    }

    def aggregateKey(AggregateKey aggregateKey) {
      this.aggregateKey = aggregateKey
      return this
    }

    def aggregateKeyHeader(BaseSheetHeader aggregateKeyHeader) {
      this.aggregateKeyHeader = aggregateKeyHeader
      return this
    }

    def sortKeys(List<SortKey> sortKeys) {
      this.sortKeys = sortKeys
      return this
    }

    def outputHeaders(List<BaseSheetHeader> outputHeaders) {
      this.outputHeaders = outputHeaders
      return this
    }

    @Override
    def build() {
      new AggregateParameters(
          originalHeaders: this.originalHeaders,
          originalFilePath: this.originalFilePath,
          expectedHeaders: this.expectedHeaders,
          expectedFilePath: this.expectedFilePath,
          groupingKeyBuilder: this.groupingKeyBuilder,
          aggregateKey: this.aggregateKey,
          aggregateKeyHeader: this.aggregateKeyHeader,
          sortKeys: this.sortKeys,
          outputHeaders: this.outputHeaders)
    }
  }
}
