package net.sympower.citizen.apx;

import net.sympower.citizen.apx.dto.incomming.Quote;
import net.sympower.citizen.apx.dto.incomming.QuoteResponse;
import net.sympower.citizen.apx.dto.incomming.QuoteValue;

import java.util.List;

public class MockData {
    private static final Quote quote1 = new Quote(
            "SomeMarket", 1573599600000L, List.of(
            new QuoteValue("Order", "1"),
            new QuoteValue("Hour", "01"),
            new QuoteValue("Net Volume", "11.11"),
            new QuoteValue("Price", "11.22")
    ));

    private static final Quote quote2 = new Quote(
            "SomeMarket", 1573599600000L, List.of(
            new QuoteValue("Order", "2"),
            new QuoteValue("Hour", "02"),
            new QuoteValue("Net Volume", "22.11"),
            new QuoteValue("Price", "22.22")
    ));

    private static final Quote quote3 = new Quote(
            "OtherMarket", 1573599600000L, List.of(
            new QuoteValue("Order", "3"),
            new QuoteValue("Hour", "03"),
            new QuoteValue("Net Volume", "33.11"),
            new QuoteValue("Price", "33.22")
    ));

    public static QuoteResponse getSameQuotes() {
        return new QuoteResponse(List.of(quote1, quote2));
    }

    public static QuoteResponse getDifferentQuotes() {
        return new QuoteResponse(List.of(quote1, quote3));
    }

}
