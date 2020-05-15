package refactoring

import groovy.json.JsonSlurper
import spock.lang.Specification

class StatementSpec extends Specification {

  def classLoader = Thread.currentThread().getContextClassLoader()

  def "Name"() {
    given:
    def invoices = new JsonSlurper().parse(classLoader.getResource("invoices.json"))
    def plays = new JsonSlurper().parse(classLoader.getResource("plays.json"))
    Statement statement = new Statement()

    when:
    def result = statement.statement(invoices[0], plays)

    then:
    result ==
'''Statement for BigCo
 Hamlet: $650.00 (55 seats)
 As You Like It: $580.00 (35 seats)
 Othello: $500.00 (40 seats)
Amount owed is $1,730.00
You earned 47.0 credits
'''

  }
}
