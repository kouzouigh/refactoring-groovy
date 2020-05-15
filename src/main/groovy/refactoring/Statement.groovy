package refactoring


import java.text.NumberFormat

class Statement {

    def statement(invoice, plays) {
        def totalAmount = 0
        def volumeCredits = 0
        def result = "Statement for ${invoice.customer}\n"
        def format = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
        format.setMinimumFractionDigits(2)
        def playFor = { aPerformance ->
            plays[aPerformance.playID]
        }
        def amountFor =  { aPerformance ->
            def amount = 0
            switch (playFor(aPerformance).type) {
                case "tragedy":
                    amount = 40000
                    if (aPerformance.audience > 30) {
                        amount += 1000 * (aPerformance.audience - 30)
                    }
                    break
                case "comedy":
                    amount = 30000
                    if (aPerformance.audience > 20) {
                        amount += 10000 + 500 * (aPerformance.audience - 20)
                    }
                    amount += 300 * aPerformance.audience
                    break
                default:
                    throw new Error("unknown type: ${playFor(aPerformance).type}")
            }
            amount
        }

        for (def perf in invoice.performances) {

            // add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0)

            // add extra credit for every ten comedy attendees
            if ("comedy" == playFor(perf).type) volumeCredits += Math.floor(perf.audience / 5)

            // print line for this order
            result += " ${playFor(perf).name}: ${format.format(amountFor(perf) / 100)} (${perf.audience} seats)\n"
            totalAmount += amountFor(perf)
        }
        result += "Amount owed is ${format.format(totalAmount / 100)}\n"
        result += "You earned ${volumeCredits} credits\n"

        return result
    }



}
