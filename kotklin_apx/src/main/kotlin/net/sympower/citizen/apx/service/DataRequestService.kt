package net.sympower.citizen.apx.service

import net.sympower.citizen.apx.dto.incomming.QuoteResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class DataRequestService {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DataRequestService::class.java)
        private var webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }

    fun getProviderData(url: String): QuoteResponse? {
        LOGGER.info("Sending ProviderName GET request to $url")
        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(QuoteResponse::class.java)
            .block()
    }

}