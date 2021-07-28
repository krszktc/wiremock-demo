package net.sympower.citizen.apx

import net.sympower.citizen.apx.dto.incomming.Quote
import net.sympower.citizen.apx.dto.incomming.QuoteResponse
import net.sympower.citizen.apx.dto.incomming.QuoteValue

object MockData {

    private val quote1 = Quote(
        "SomeMarket", 1573599600000, listOf(
            QuoteValue("Order", "1"),
            QuoteValue("Hour", "01"),
            QuoteValue("Net Volume", "11.11"),
            QuoteValue("Price", "11.22")
        )
    )

    private val quote2 = Quote(
        "SomeMarket", 1573599600000, listOf(
            QuoteValue("Order", "2"),
            QuoteValue("Hour", "02"),
            QuoteValue("Net Volume", "22.11"),
            QuoteValue("Price", "22.22")
        )
    )

    private val quote3 = Quote(
        "OtherMarket", 1573599600000, listOf(
            QuoteValue("Order", "3"),
            QuoteValue("Hour", "03"),
            QuoteValue("Net Volume", "33.11"),
            QuoteValue("Price", "33.22")
        )
    )

    fun getSameQuotes(): QuoteResponse =
        QuoteResponse(listOf(quote1, quote2))

    fun getDifferentQuotes(): QuoteResponse =
        QuoteResponse(listOf(quote1, quote3))

}