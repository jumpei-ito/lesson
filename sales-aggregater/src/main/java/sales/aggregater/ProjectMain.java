package sales.aggregater;

import aggregate.core.AggregateApplication;
import aggregate.core.service.BaseAggregateService;

public class ProjectMain {

  public static void main(String[] args) {
    System.out.println("Start Sales Aggregater.");

    AggregateApplication application = new AggregateApplication(SalesAggregateConfig.class);
    BaseAggregateService service = application.getBean(BaseAggregateService.class);
    String csvFilePath = application.getProperty("csvFilePath");

    service.aggregate(application.getAggregaters(), csvFilePath);

    System.out.println("End Sales Aggregater.");
  }

}
