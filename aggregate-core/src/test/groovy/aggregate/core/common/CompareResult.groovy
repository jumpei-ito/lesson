package aggregate.core.common

class CompareResult {

  boolean isDifferent
  List<String> diffs = new ArrayList()

  CompareResult(String headerDiff) {
    diffs.add(headerDiff)
  }

  void addDiff(String diff) {
    diffs.add(diff)
    isDifferent = true
  }
}
