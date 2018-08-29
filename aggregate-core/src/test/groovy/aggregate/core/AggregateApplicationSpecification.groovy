package aggregate.core

import spock.lang.Specification
import spock.lang.Unroll

class AggregateApplicationSpecification extends Specification {

  @Unroll
  def "Throwing RuntimeException Caused by #title"() {
    when:
    def application = new AggregateApplication(clazz)

    then:
    def e = thrown(RuntimeException)
    println(e)

    where:
    title                                 | clazz
    "Null Config"                         | null
    "not Extending AggregateConfig Class" | AggregateConfig.class
  }
}
