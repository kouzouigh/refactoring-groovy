package refactoring

import java.text.NumberFormat

class Statement {

    def usd = { aNumber ->
        def format = NumberFormat.getCurrencyInstance(new Locale("en", "US"))
        format.setMinimumFractionDigits(2)
        return format.format(aNumber / 100)
    }

    def statement(invoice, plays) {
        return renderPlainText(CreateStatementData.from(invoice, plays))
    }

    def htmlStatement(invoice, plays) {
        return renderHtml(CreateStatementData.from(invoice, plays));
    }

    private def renderPlainText(data) {
        def statementResult = "Statement for ${data.customer}\n"
        for (def perf in data.performances) {
            statementResult += " ${perf.play.name}: ${usd(perf.amount)} (${perf.audience} seats)\n"
        }
        statementResult += "Amount owed is ${usd(data.totalAmount)}\n"
        statementResult += "You earned ${data.totalVolumeCredits} credits\n"
        return statementResult
    }

    def renderHtml(data) {
        def result = "<h1>Statement for ${data.customer}</h1>\n"
        result += "<table>\n"
        result += "<tr><th>play</th><th>seats</th><th>cost</th></tr>"
        for (def perf in data.performances) {
            result += " <tr><td>${perf.play.name}</td><td>${perf.audience}</td>"
            result += "<td>${usd(perf.amount)}</td></tr>\n"
        }
        result += "</table>\n"
        result += "<p>Amount owed is <em>${usd(data.totalAmount)}</em></p>\n"
        result += "<p>You earned <em>${data.totalVolumeCredits}</em> credits</p>\n"
        return result
    }

}
