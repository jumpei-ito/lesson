package sales.aggregater.service;

import org.springframework.stereotype.Component;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.service.BaseAggregateService;
import sales.aggregater.constant.SalesSheetHeader;

@Component
public class SalesAggregateService extends BaseAggregateService {

  @Override
  protected BaseSheetHeader[] getSheetHeader() {
    return SalesSheetHeader.values();
  }

}
