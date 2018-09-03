package aggregate.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.Constant;
import aggregate.core.io.StdOutWriter;
import aggregate.core.io.Writer;

/**
 * Abstract config class for Spring.<br>
 * It is necessary that
 */
@Configuration
@ComponentScan("aggregate.core")
public abstract class AggregateConfig {

  /**
   * Bean of writer class.
   *
   * @return Writer
   */
  @Bean
  public Writer writer() {
    return new StdOutWriter();
  }

  /**
   * Property of original csv file path.
   *
   * @return Original csv file path
   */
  @Bean(name = Constant.P_CSV_FILE_PATH)
  public abstract String csvFilePath();

  /**
   *
   * @return
   */
  @Bean
  public abstract BaseSheetHeader[] csvFileHeader();

}
