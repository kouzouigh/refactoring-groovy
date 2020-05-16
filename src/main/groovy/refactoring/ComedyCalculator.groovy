package refactoring

class ComedyCalculator extends PerformanceCalculator {

  ComedyCalculator(Object aPerformance, Object aPlay) {
    super(aPerformance, aPlay)
  }

  @Override
  def getAmount() {
    def result = 30000
    if (performance.audience > 20) {
      result += 10000 + 500 * (performance.audience - 20)
    }
    result += 300 * performance.audience
    result
  }

  @Override
  def getVolumeCredits() {
    super.volumeCredits + Math.floor(this.performance.audience / 5)
  }
}
