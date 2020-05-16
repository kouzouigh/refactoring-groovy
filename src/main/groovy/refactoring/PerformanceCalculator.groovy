package refactoring

abstract class PerformanceCalculator {

  def performance

  def play

  PerformanceCalculator(aPerformance, aPlay) {
    this.performance = aPerformance
    this.play = aPlay
  }

  abstract def getAmount()

  def getVolumeCredits() {
    Math.max(this.performance.audience - 30, 0)
  }

}
