package aggregate.core.common

import aggregate.core.constant.BaseSheetHeader
import aggregate.core.model.AggregateKey
import aggregate.core.model.SortKey
import aggregate.core.model.grouping.GroupingKeysBuilder

class AggregateParameters {

  /** Headers of original csv file */
  BaseSheetHeader[] originalHeaders
  /**  */
  String originalFilePath
  /** Headers of expected aggregate result */
  BaseSheetHeader[] expectedHeaders
  /**  */
  String expectedFilePath
  /**  */
  GroupingKeysBuilder groupingKeyBuilder
  /**  */
  AggregateKey aggregateKey
  /**  */
  BaseSheetHeader aggregateKeyHeader
  /**  */
  List<SortKey> sortKeys
}
