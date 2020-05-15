package refactoring


import java.text.NumberFormat

class Statement {

    def statement(def invoice, def plays) {
        def totalAmount = 0
        def volumeCredits = 0
        def result = "Statement for ${invoice.customer}\n"
        def format = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
        format.setMinimumFractionDigits(2)

        /*def format = new Intl.NumberFormat("en-US",
                { style: "currency", currency: "USD",
                    minimumFractionDigits: 2 }).format;*/
        for (def perf in invoice.performances) {
            final def play = plays[perf.playID]
            def thisAmount = 0
            switch (play.type) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience - 30)
                    }
                    break
                case "comedy":
                    thisAmount = 30000
                    if (perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20)
                    }
                    thisAmount += 300 * perf.audience
                    break
                default:
                    throw new Error("unknown type: ${play.type}")
            }
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


}
