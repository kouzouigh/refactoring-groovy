package refactoring

class CreateStatementData {

  static def from(invoice, plays) {
    def playFor = { aPerformance ->
      plays[aPerformance.playID]
    }
    def enrichPerformance = { aPerformance ->
      final def calculator = new PerformanceCalculator(aPerformance, playFor(aPerformance))
      def result = [:]
      result << aPerformance
      result.play = calculator.play
      result.amount = calculator.amount
      result.volumeCredits = calculator.volumeCredits
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
