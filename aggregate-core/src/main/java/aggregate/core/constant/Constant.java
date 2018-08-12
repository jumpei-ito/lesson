package aggregate.core.constant;

import java.text.DecimalFormat;

/**
 * 定数を保持するクラス
 */
public class Constant {

  /** カンマ（,） */
  public static final String COMMA = ",";
  /** ダブルクオーテーション（"） */
  public static final String DOUBLE_QUOTATION = "\"";
  /** 数値カラムのフォーマット */
  public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###.##");
  /** 金額カラムのフォーマット */
  public static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###円");
  /** 割合カラムのフォーマット */
  public static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("##0.0%");
  /** CSVファイルのパスを表すプロパティ名 */
  public static final String P_CSV_FILE_PATH = "csvFilePath";

}
