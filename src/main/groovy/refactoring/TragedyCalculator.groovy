package refactoring

class TragedyCalculator extends PerformanceCalculator {

  TragedyCalculator(Object aPerformance, Object aPlay) {
    super(aPerformance, aPlay)
  }

  @Override
  def getAmount() {
    def result = 40000
    if (performance.audience > 30) {
      result += 1000 * (performance.audience - 30)
    }
    result
  }
}
