package aggregate.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger class for standard output.
 */
public class AggregateLogger {

  private static final Logger logger = LoggerFactory.getLogger(AggregateLogger.class);

  /**
   * Constructor
   */
  private AggregateLogger() {
    super();
  }

  /**
   * Output information logs.
   *
   * @param msg log message
   */
  public static void info(String msg) {
    logger.info(msg);
  }

  /**
   * Output debug logs.
   *
   * @param msg log message
   */
  public static void debug(String msg) {
    logger.debug(msg);
  }

}
