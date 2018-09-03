package aggregate.core.constant;

import java.text.DecimalFormat;

/**
 * Constant holder class.
 */
public class Constant {

  /** Comma（,） */
  public static final String COMMA = ",";
  /** Double quotation（"） */
  public static final String DOUBLE_QUOTATION = "\"";
  /** Format of number column. */
  public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###.##");
  /** Format of currency column. */
  public static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###円");
  /** Format of rate column. */
  public static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("##0.0%");
  /** Property name of original csv file path. */
  public static final String P_CSV_FILE_PATH = "csvFilePath";

}
