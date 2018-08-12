package aggregate.core;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import aggregate.core.service.BaseAggregator;

public class AggregateApplication {

  private final AnnotationConfigApplicationContext context;
  private final List<BaseAggregator> aggregators;

  public AggregateApplication(Class<?> clazz) {
    validateConfigClass(clazz);
    context = new AnnotationConfigApplicationContext(clazz);
    aggregators = getAggregators(context);
  }

  public List<BaseAggregator> getAggregators() {
    return aggregators;
  }

  public <T> T getBean(Class<T> clazz) {
    return context.getBean(clazz);
  }

  public String getProperty(String name) {
    return context.getBean(name, String.class);
  }

  private void validateConfigClass(Class<?> clazz) {
    if (clazz == null) {
      throw new RuntimeException("Config class is null.");
    }
    String superClass = clazz.getSuperclass().getSimpleName();
    if (!AggregateConfig.class.getSimpleName().equals(superClass)) {
      throw new RuntimeException("Config class doesn't extend AggregateConfig: " + superClass);
    }
  }

  private List<BaseAggregator> getAggregators(AnnotationConfigApplicationContext context) {
    return context.getBeansOfType(BaseAggregator.class).values().stream()
        .sorted((a1, a2) -> a1.getExecuteOrder() - a2.getExecuteOrder())
        .collect(Collectors.toList());
  }

}
