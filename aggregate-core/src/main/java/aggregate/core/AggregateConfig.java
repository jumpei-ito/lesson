package aggregate.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.Constant;
import aggregate.core.io.StdOutWriter;
import aggregate.core.io.Writer;

/**
 * Config class for Spring.
 */
@Configuration
@ComponentScan("aggregate.core")
public class AggregateConfig {

  private Properties properties = loadProperties();

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
   * Property of package name to scan Spring components for subproject.<br>
   * Returns empty string if property is not set.
   *
   * @return Package name to scan components
   */
  @Bean(name = Constant.P_SCAN_PACKAGE)
  public String scanPackage() {
    String packageName = properties.getProperty("scan.package");
    return packageName == null ? "" : packageName;
  }

  /**
   * Property of original csv file path.
   *
   * @return Original csv file path
   */
  @Bean(name = Constant.P_ORIGINAL_CSV_PATH)
  public String originalCsvPath() {
    String csvPath = properties.getProperty("original.csv.path");
    if (csvPath == null) {
      throw new RuntimeException("Original csv path setting is not found in properties file.");
    }
    return csvPath;
  }

  /**
   * Property of original csv file header.
   *
   * @return Original csv file header
   */
  @Bean
  public BaseSheetHeader[] originalCsvHeader() {
    String headerClass = properties.getProperty("sheet.header.class");
    try {
      Class<?> clazz = Class.forName(headerClass);
      return (BaseSheetHeader[]) clazz.getEnumConstants();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private Properties loadProperties() {
    try (InputStream inputStream = new FileInputStream(getPropertiesFileName())) {
      Properties properties = new Properties();
      properties.load(inputStream);
      return properties;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected String getPropertiesFileName() {
    return "bin/aggregate.properties";
  }

}
