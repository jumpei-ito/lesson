package aggregate.core;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig extends AggregateConfig {

  @Override
  protected String getPropertiesFileName() {
    return "bin/aggregate-test.properties";
  }

}
