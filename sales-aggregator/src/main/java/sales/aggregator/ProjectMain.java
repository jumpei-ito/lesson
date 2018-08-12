package sales.aggregator;

import aggregate.core.AggregateApplication;
import aggregate.core.service.AggregateService;

public class ProjectMain {

  public static void main(String[] args) {
    System.out.println("Start Sales Aggregator.");
    // Spring boot up
    AggregateApplication application = new AggregateApplication(SalesAggregateConfig.class);
    // aggregate
    AggregateService service = application.getBean(AggregateService.class);
    service.aggregate(application);

    System.out.println("End Sales Aggregator.");
  }

}
