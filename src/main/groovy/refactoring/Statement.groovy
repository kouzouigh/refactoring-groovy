package refactoring


import java.text.NumberFormat

class Statement {

    def statement(invoice, plays) {
        def totalAmount = 0
        def statementResult = "Statement for ${invoice.customer}\n"
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
        def usd = { aNumber ->
            def format = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
            format.setMinimumFractionDigits(2)
            return format.format(aNumber / 100)
        }
        def totalVolumeCredits = {
            def volumeCredits = 0
            for (def perf in invoice.performances) {
                volumeCredits += volumeCreditsFor(perf)
            }
            volumeCredits
        }

        for (def perf in invoice.performances) {
            // print line for this order
            statementResult += " ${playFor(perf).name}: ${usd(amountFor(perf))} (${perf.audience} seats)\n"
            totalAmount += amountFor(perf)
        }

        statementResult += "Amount owed is ${usd(totalAmount)}\n"
        statementResult += "You earned ${totalVolumeCredits()} credits\n"

        return statementResult
    }

}
