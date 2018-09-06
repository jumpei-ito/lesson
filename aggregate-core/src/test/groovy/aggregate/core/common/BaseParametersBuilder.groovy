package aggregate.core.common

import aggregate.core.constant.BaseSheetHeader

class BaseParametersBuilder {

  /** set default value */
  BaseSheetHeader[] originalHeaders
  /** set default value */
  String originalFilePath
  BaseSheetHeader[] expectedHeaders
  String expectedFilePath

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

  def build() {
    new BaseParameters(
        originalHeaders: this.originalHeaders,
        originalFilePath: this.originalFilePath,
        expectedHeaders: this.expectedHeaders,
        expectedFilePath: this.expectedFilePath,)
  }
}
