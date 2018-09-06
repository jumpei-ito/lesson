package aggregate.core.common

import aggregate.core.constant.BaseSheetHeader

class BaseParameters {
  /** Headers of original csv file */
  BaseSheetHeader[] originalHeaders
  /**  */
  String originalFilePath
  /** Headers of expected aggregate result */
  BaseSheetHeader[] expectedHeaders
  /**  */
  String expectedFilePath

  static def builder() {
    new BaseParametersBuilder()
  }
}
