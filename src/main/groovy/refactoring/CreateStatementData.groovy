package refactoring

class CreateStatementData {

  static def from(invoice, plays) {
    def playFor = { aPerformance ->
      plays[aPerformance.playID]
    }
    def enrichPerformance = { aPerformance ->
      final def calculator = createPerformanceCalculator(aPerformance, playFor(aPerformance))
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

  static def createPerformanceCalculator(aPerformance, aPlay) {
    switch (aPlay.type) {
      case "tragedy": return new TragedyCalculator(aPerformance, aPlay)
      case "comedy": return new ComedyCalculator(aPerformance, aPlay)
      default:
        throw new Exception("unknown type: ${aPlay.type}")
    }
  }

}
