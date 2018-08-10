package aggregate.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import aggregate.core.io.StdOutWriter;
import aggregate.core.io.Writer;

@Configuration
@ComponentScan("aggregate.core")
public abstract class AggregateConfig {

  @Bean
  public Writer writer() {
    return new StdOutWriter();
  }

  @Bean(name = "csvFilePath")
  public abstract String csvFilePath();

}
