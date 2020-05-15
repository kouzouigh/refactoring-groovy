package refactoring


import java.text.NumberFormat

class Statement {

    def statement(invoice, plays) {
        def totalAmount = 0
        def volumeCredits = 0
        def statementResult = "Statement for ${invoice.customer}\n"
        def format = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
        format.setMinimumFractionDigits(2)
        def playFor = { aPerformance ->
            plays[aPerformance.playID]
        }
        def amountFor =  { aPerformance ->
            def result = 0
            switch (playFor(aPerformance).type) {
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
                    throw new Error("unknown type: ${playFor(aPerformance).type}")
            }
            result
        }
        def volumeCreditsFor = { aPerformance ->
            def result = 0
            result += Math.max(aPerformance.audience - 30, 0)
            if ("comedy" == playFor(aPerformance).type) result += Math.floor(aPerformance.audience / 5)
            result
        }

        for (def perf in invoice.performances) {
            volumeCredits += volumeCreditsFor(perf)

            // print line for this order
            statementResult += " ${playFor(perf).name}: ${format.format(amountFor(perf) / 100)} (${perf.audience} seats)\n"
            totalAmount += amountFor(perf)
        }
        statementResult += "Amount owed is ${format.format(totalAmount / 100)}\n"
        statementResult += "You earned ${volumeCredits} credits\n"

        return statementResult
    }

}
