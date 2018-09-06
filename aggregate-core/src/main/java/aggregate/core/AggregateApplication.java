package aggregate.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;
import aggregate.core.constant.Constant;
import aggregate.core.service.BaseAggregator;
import aggregate.core.util.AggregateLogger;

/**
 * Class to control Spring context.
 */
public class AggregateApplication {

  private final AnnotationConfigApplicationContext context;
  private final List<BaseAggregator> aggregators;

  /**
   * Constructor
   */
  public AggregateApplication() {
    context = new AnnotationConfigApplicationContext(AggregateConfig.class);
    // scan project package
    scanAdditionalPackage(context);
    aggregators = getAggregators(context);
  }

  /**
   * Constructor
   *
   * @param clazz Config class
   */
  public AggregateApplication(Class<?> clazz) {
    validateConfigClass(clazz);
    context = new AnnotationConfigApplicationContext(clazz);
    // scan project package
    scanAdditionalPackage(context);
    aggregators = getAggregators(context);
  }

  /**
   * Getter of aggregators.
   *
   * @return Aggregator instances implements {@link BaseAggregator}
   */
  public List<BaseAggregator> getAggregators() {
    return aggregators;
  }

  /**
   * Getter of Bean.
   *
   * @param clazz Bean class
   * @return Bean instance
   */
  public <T> T getBean(Class<T> clazz) {
    return context.getBean(clazz);
  }

  /**
   * Getter of property from Bean.
   *
   * @param name Property name
   * @return Property value
   */
  public String getProperty(String name) {
    return context.getBean(name, String.class);
  }

  private void validateConfigClass(Class<?> clazz) {
    if (clazz == null) {
      throw new RuntimeException("Config class is null.");
    }
    // Config class must extend AggregateConfig.
    String superClass = clazz.getSuperclass().getSimpleName();
    if (!AggregateConfig.class.getSimpleName().equals(superClass)) {
      throw new RuntimeException("Config class doesn't extend AggregateConfig: " + superClass);
    }
  }

  private void scanAdditionalPackage(AnnotationConfigApplicationContext context) {
    String packageName = getProperty(Constant.P_SCAN_PACKAGE);
    if (!StringUtils.isEmpty(packageName)) {
      context.scan(packageName);
    }
  }

  private List<BaseAggregator> getAggregators(AnnotationConfigApplicationContext context) {
    Map<String, BaseAggregator> aggregatorBeans = context.getBeansOfType(BaseAggregator.class);
    if (aggregatorBeans.size() == 0) {
      AggregateLogger.info("Aggregator class is not found.");
    }
    return aggregatorBeans.values().stream()
        .sorted((a1, a2) -> a1.getExecuteOrder() - a2.getExecuteOrder())
        .collect(Collectors.toList());
  }

}
