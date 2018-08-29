package aggregate.core.service

import aggregate.core.common.BaseAggregateSpecification

class AggregateServiceSpecification extends BaseAggregateSpecification {

  AggregateService service

  def setup() {
    service = application.getBean(AggregateService.class)
  }

  def "AggregateService does not Throw Any Exceptions"() {
    when:
    service.aggregate(application)

    then:
    notThrown(Exception)
  }
}
