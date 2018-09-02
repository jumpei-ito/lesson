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

  static class AggregateParametersBuilder {
    /** set default value */
    BaseSheetHeader[] originalHeaders = Constant.ORIGINAL_HEADERS
    /** set default value */
    String originalFilePath = Constant.ORIGINAL_FILE_PATH
    BaseSheetHeader[] expectedHeaders
    String expectedFilePath
    GroupingKeysBuilder groupingKeyBuilder
    AggregateKey aggregateKey
    BaseSheetHeader aggregateKeyHeader
    List<SortKey> sortKeys
    List<BaseSheetHeader> outputHeaders

    def originalHeaders(BaseSheetHeader[] originalHeaders) {
      this.originalHeaders = originalHeaders
      return this
    }

    def originalFilePath(String originalFilePath) {
      this.originalFilePath = originalFilePath
      return this
    }

    def expectedHeaders(BaseSheetHeader[] expectedHeaders) {
      this.expectedHeaders = expectedHeaders
      return this
    }

    def expectedFilePath(String expectedFilePath) {
      this.expectedFilePath = expectedFilePath
      return this
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
