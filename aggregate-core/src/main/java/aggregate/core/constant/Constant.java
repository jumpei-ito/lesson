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
  public static DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###.##");
  /** 金額カラムのフォーマット */
  public static DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###円");
  /** 割合カラムのフォーマット */
  public static DecimalFormat PERCENT_FORMAT = new DecimalFormat("##0.0%");

}
