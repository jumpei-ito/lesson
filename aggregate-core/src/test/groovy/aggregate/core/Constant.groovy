package aggregate.core

import aggregate.core.constant.SortType
import aggregate.core.constant.TestSalesSheetHeader
import aggregate.core.model.SortKey
import aggregate.core.model.grouping.GroupingKeysBuilder

class Constant {
  /**  */
  static def ORIGINAL_HEADERS = TestSalesSheetHeader.values()
  /**  */
  static String ORIGINAL_FILE_PATH = "bin/TestSalesList.csv"
  /**  */
  static String QUOTE = "\""

  static def BUILDER_GROUING_BY_PERSON_AND_ITEM = groupingKeyBuilder1

  static private def getGroupingKeyBuilder1() {
    GroupingKeysBuilder builder = new GroupingKeysBuilder();
    builder.addHeaderKey(TestSalesSheetHeader.PERSON);
    builder.addHeaderKey(TestSalesSheetHeader.ITEM_CODE);
    return builder;
  }

  static def SORT_KEY_BY_PERSON_AND_ITEM = sortKeys1

  static private def getSortKeys1() {
    Arrays.asList(new SortKey(TestSalesSheetHeader.PERSON, SortType.ASC),
        new SortKey(TestSalesSheetHeader.ITEM_CODE, SortType.ASC));
  }
}
