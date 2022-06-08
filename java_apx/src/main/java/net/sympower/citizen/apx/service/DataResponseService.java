package net.sympower.citizen.apx.service;

import net.sympower.citizen.apx.config.ProviderEndpointsProperty;
import net.sympower.citizen.apx.dto.incomming.Quote;
import net.sympower.citizen.apx.dto.incomming.QuoteValue;
import net.sympower.citizen.apx.dto.outgoing.DataResponse;
import net.sympower.citizen.apx.dto.outgoing.DataResponseValue;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataResponseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataResponseService.class);

    private static final String HOUR_LABEL = "Order";
    private static final String PRICE_LABEL = "Price";
    private static final String NET_VOLUME_LABEL = "Net Volume";

    private final DataRequestService dataRequestService;
    private final ProviderEndpointsProperty providerEndpointsProperty;

    public DataResponseService(
            DataRequestService dataRequestService,
            ProviderEndpointsProperty providerEndpointsProperty
    ) {
        this.dataRequestService = dataRequestService;
        this.providerEndpointsProperty = providerEndpointsProperty;
    }

    public List<DataResponse> getDataResponse() {
        try {
            return requestProviderData();
        } catch (WebClientResponseException exception) {
            // Do something or just by demo scenario return ...
            return List.of();
        }
    }

    List<DataResponse> requestProviderData() {
        Map<Pair<String, Long>, List<Quote>> groupedData = dataRequestService.getProviderData(providerEndpointsProperty.getSomeProviderGet())
                .getQuote().stream()
                .collect(Collectors.groupingBy(row -> Pair.of(row.getMarket(), row.getDate_applied())));

        if (!groupedData.isEmpty()) {
            return groupedData.keySet().stream()
                    .map(dataKey -> {
                        LOGGER.info("Proceed conversion market: {}, timestamp: {}", dataKey.getLeft(), dataKey.getRight());
                        List<DataResponseValue> dataResponseValues = groupedData.get(dataKey).stream()
                                .map(quote -> {
                                    String hour = quote.getValues().stream()
                                            .filter(value -> value.gettLabel().equals(HOUR_LABEL))
                                            .findFirst().get().getValue();
                                    String price = quote.getValues().stream()
                                            .filter(value -> value.gettLabel().equals(PRICE_LABEL))
                                            .findFirst().get().getValue();
                                    String netVolume = quote.getValues().stream()
                                            .filter(value -> value.gettLabel().equals(NET_VOLUME_LABEL))
                                            .findFirst().get().getValue();
                                    return new DataResponseValue(Integer.parseInt(hour), new BigDecimal(netVolume), new BigDecimal(price));
                                })
                                .collect(Collectors.toList());
                        return new DataResponse(dataKey.getLeft(), dataKey.getRight(), dataResponseValues);
                    })
                    .collect(Collectors.toList());
        }
        return List.of();
    }

}
