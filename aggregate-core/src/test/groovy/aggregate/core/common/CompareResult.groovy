package aggregate.core.common

class CompareResult {

  boolean isDifferent
  List<String> diffs = new ArrayList()
  String expectedFilePath

  CompareResult(String headerDiff, String expectedFilePath) {
    diffs.add(headerDiff)
    this.expectedFilePath = expectedFilePath
  }

  void addDiff(String diff) {
    diffs.add(diff)
    isDifferent = true
  }
}
