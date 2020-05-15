package refactoring

import groovy.json.JsonSlurper
import spock.lang.Shared
import spock.lang.Specification

class StatementSpec extends Specification {

  @Shared
  def classLoader = Thread.currentThread().getContextClassLoader()

  @Shared
  def invoices = new JsonSlurper().parse(classLoader.getResource("invoices.json"))

  @Shared
  def plays = new JsonSlurper().parse(classLoader.getResource("plays.json"))

  def "render plain text"() {
    given:
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

  def "render html"() {
    given:
    Statement statement = new Statement()

    when:
    def result = statement.htmlStatement(invoices[0], plays)

    then:
    result ==
        '''<h1>Statement for BigCo</h1>
<table>
<tr><th>play</th><th>seats</th><th>cost</th></tr> <tr><td>Hamlet</td><td>55</td><td>$650.00</td></tr>
 <tr><td>As You Like It</td><td>35</td><td>$580.00</td></tr>
 <tr><td>Othello</td><td>40</td><td>$500.00</td></tr>
</table>
<p>Amount owed is <em>$1,730.00</em></p>
<p>You earned <em>47.0</em> credits</p>
'''
  }
}
