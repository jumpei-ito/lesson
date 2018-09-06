package sales.aggregator;

import aggregate.core.AggregateApplication;
import aggregate.core.service.AggregateService;
import aggregate.core.util.AggregateLogger;

public class ProjectMain {

  public static void main(String[] args) {
    AggregateLogger.info("Start Sales Aggregator.");
    // Spring boot up
    AggregateApplication application = new AggregateApplication();
    // aggregate
    AggregateService service = application.getBean(AggregateService.class);
    service.aggregate(application);

    AggregateLogger.info("End Sales Aggregator.");
  }

}
