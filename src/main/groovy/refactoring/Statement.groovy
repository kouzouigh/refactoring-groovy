package refactoring


import java.text.NumberFormat

class Statement {

    def statement(invoice, plays) {
        def totalAmount = 0
        def volumeCredits = 0
        def result = "Statement for ${invoice.customer}\n"
        def format = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
        format.setMinimumFractionDigits(2)
        for (def perf in invoice.performances) {
            final def play = plays[perf.playID]
            def thisAmount = amountFor(perf, play)

            // add volume credits
            volumeCredits += Math.max(perf.audience - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.type) volumeCredits += Math.floor(perf.audience / 5)
            // print line for this order
            result += " ${play.name}: ${format.format(thisAmount / 100)} (${perf.audience} seats)\n"
            totalAmount += thisAmount;
        }
        result += "Amount owed is ${format.format(totalAmount / 100)}\n"
        result += "You earned ${volumeCredits} credits\n"
        return result;
    }

    private def amountFor(aPerformance, play) {
        def result = 0
        switch (play.type) {
            case "tragedy":
                result = 40000;
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
                throw new Error("unknown type: ${play.type}")
        }
        result
    }


}
