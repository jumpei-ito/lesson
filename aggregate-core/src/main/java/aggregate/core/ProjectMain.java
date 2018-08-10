package aggregate.core;

import aggregate.core.service.BaseAggregateService;

public class ProjectMain {

  public static void main(String[] args) {
    System.out.println("Start Aggregater.");

    AggregateApplication application = new AggregateApplication();
    BaseAggregateService service = application.getBean(BaseAggregateService.class);
    String csvFilePath = application.getProperty("csvFilePath");

    service.aggregate(application.getAggregaters(), csvFilePath);

    System.out.println("End Aggregater.");
  }

}
