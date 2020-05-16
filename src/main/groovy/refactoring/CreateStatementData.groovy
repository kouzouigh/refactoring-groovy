package refactoring

class CreateStatementData {

  static def from(invoice, plays) {
    def playFor = { aPerformance ->
      plays[aPerformance.playID]
    }
    def amountFor = { aPerformance ->
      def result = 0
      switch (aPerformance.play.type) {
        case "tragedy":
          result = 40000
          if (aPerformance.audience > 30) {
            result += 1000 * (aPerformance.audience - 30)
          }
          break
        case "comedy":
          result = 30000
          if (aPerformance.audience > 20) {
            result += 10000 + 500 * (aPerformance.audience - 20)
          }
          result += 300 * aPerformance.audience
          break
        default:
          throw new Error("unknown type: ${aPerformance.play.type}")
      }
      result
    }
    def volumeCreditsFor = { aPerformance ->
      def result = 0
      result += Math.max(aPerformance.audience - 30, 0)
      if ("comedy" == aPerformance.play.type) result += Math.floor(aPerformance.audience / 5)
      result
    }
    def enrichPerformance = { aPerformance ->
      final def calculator = new PerformanceCalculator(aPerformance, playFor(aPerformance))
      def result = [:]
      result << aPerformance
      result.play = calculator.play
      result.amount = amountFor(result)
      result.volumeCredits = volumeCreditsFor(result)
      result
    }

    def totalVolumeCredits = { data ->
      data.performances.sum { perf -> perf.volumeCredits }
    }
    def totalAmount = { data ->
      data.performances.sum { perf -> perf.amount }
    }

    def statementData = [:]
    statementData.customer = invoice.customer
    statementData.performances = invoice.performances.collect(enrichPerformance)
    statementData.totalAmount = totalAmount(statementData)
    statementData.totalVolumeCredits = totalVolumeCredits(statementData)
    statementData
  }

}
