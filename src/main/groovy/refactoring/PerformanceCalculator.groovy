package refactoring

class PerformanceCalculator {

  def performance

  def play

  PerformanceCalculator(aPerformance, aPlay) {
    this.performance = aPerformance
    this.play = aPlay
  }

  def getAmount() {
    def result = 0
    switch (this.play.type) {
      case "tragedy":
        result = 40000
        if (performance.audience > 30) {
          result += 1000 * (performance.audience - 30)
        }
        break
      case "comedy":
        result = 30000
        if (performance.audience > 20) {
          result += 10000 + 500 * (performance.audience - 20)
        }
        result += 300 * performance.audience
        break
      default:
        throw new Error("unknown type: ${this.play.type}")
    }
    result
  }

  def getVolumeCredits() {
    def result = 0
    result += Math.max(this.performance.audience - 30, 0)
    if ("comedy" == this.play.type) result += Math.floor(this.performance.audience / 5)
    result
  }

}
