package net.sympower.citizen.apx.service

import net.sympower.citizen.apx.config.ProviderEndpointsProperty
import net.sympower.citizen.apx.dto.outgoing.DataResponse
import net.sympower.citizen.apx.dto.outgoing.DataResponseValue
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientResponseException
import java.math.BigDecimal

@Service
class DataResponseService(
    private val dataRequestService: DataRequestService,
    private val providerEndpoints: ProviderEndpointsProperty
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DataRequestService::class.java)
        private const val HOUR_LABEL = "Order"
        private const val PRICE_LABEL = "Price"
        private const val NET_VOLUME_LABEL = "Net Volume"
    }

    fun getDataResponse(): List<DataResponse> {
        return try {
            requestProviderData()
        } catch (exception: WebClientResponseException) {
            // Do something or just by demo scenario return ...
            emptyList()
        }
    }

    fun requestProviderData(): List<DataResponse> =
        dataRequestService.getProviderData(providerEndpoints.someProviderGet)?.quote
            ?.groupBy { Pair(it.market, it.date_applied) }
            ?.map { market ->
                LOGGER.info("Proceed conversion market: ${market.key.first}, timestamp: ${market.key.second}")
                DataResponse(
                    market.key.first,
                    market.key.second,
                    market.value.map { marketValue ->
                        val hour = marketValue.values.first { it.tLabel == HOUR_LABEL }
                        val price = marketValue.values.first { it.tLabel == PRICE_LABEL }
                        val netVolume = marketValue.values.first { it.tLabel == NET_VOLUME_LABEL }
                        DataResponseValue(hour.value.toInt(), BigDecimal(netVolume.value), BigDecimal(price.value))
                    }
                )
            }
            ?: emptyList()
}