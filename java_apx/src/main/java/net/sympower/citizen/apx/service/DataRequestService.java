package net.sympower.citizen.apx.service;

import net.sympower.citizen.apx.dto.incomming.QuoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DataRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRequestService.class);
    private final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public QuoteResponse getProviderData(String url) {
        LOGGER.info("Sending ProviderName GET request to $url");
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(QuoteResponse.class)
            .block();
    }

}
