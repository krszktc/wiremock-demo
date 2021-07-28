package net.sympower.citizen.apx

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import de.mkammerer.wiremock.WireMockExtension
import net.sympower.citizen.apx.service.DataRequestService
import org.apache.commons.io.FileUtils
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClientResponseException


@SpringBootTest
class DataRequestServiceTest {

    @JvmField
    @RegisterExtension
    final var wireMock = WireMockExtension()

    @Autowired
    private lateinit var service: DataRequestService

    @Value("classpath:apx-data.json")
    private lateinit var apxData: Resource


    @Test
    fun `should get response from mocked provider`() {
        // GIVEN
        val jsonString = FileUtils.readFileToString(apxData.file)
        val mockUrl = getPreconfiguredMockWebServer("/some/endpoint", HttpStatus.OK_200, jsonString)
        // WHEN
        val response = service.getProviderData(mockUrl)
        // THEN
        assertThat(response?.quote).hasSize(24)
        assertThat( response?.quote?.get(4)).isNotNull
        response?.quote?.get(4)?.let {
            assertThat(it.market).isEqualTo("APX Power NL Hourly")
            assertThat(it.date_applied).isEqualTo(1573599600000)
            assertThat(it.values).hasSize(4)
            assertThat(it.values[0].tLabel).isEqualTo("Order")
            assertThat(it.values[0].value).isEqualTo("5")
            assertThat(it.values[1].tLabel).isEqualTo("Hour")
            assertThat(it.values[1].value).isEqualTo("05")
            assertThat(it.values[2].tLabel).isEqualTo("Net Volume")
            assertThat(it.values[2].value).isEqualTo("4838.70")
            assertThat(it.values[3].tLabel).isEqualTo("Price")
            assertThat(it.values[3].value).isEqualTo("34.72")
        }
        assertThat( response?.quote?.get(4)).isNotNull
        response?.quote?.get(18)?.let {
            assertThat(it.market).isEqualTo("APX Power NL Hourly")
            assertThat(it.date_applied).isEqualTo(1573599600000)
            assertThat(it.values).hasSize(4)
            assertThat(it.values[0].tLabel).isEqualTo("Order")
            assertThat(it.values[0].value).isEqualTo("19")
            assertThat(it.values[1].tLabel).isEqualTo("Hour")
            assertThat(it.values[1].value).isEqualTo("19")
            assertThat(it.values[2].tLabel).isEqualTo("Net Volume")
            assertThat(it.values[2].value).isEqualTo("3384.30")
            assertThat(it.values[3].tLabel).isEqualTo("Price")
            assertThat(it.values[3].value).isEqualTo("56.09")
        }
    }

    @Test
    fun `should throw exception by provider issues`() {
        // GIVEN
        val mockUrl = getPreconfiguredMockWebServer("/broken/endpoint", HttpStatus.BAD_GATEWAY_502, "{ \"error\": \"BOOOOOM!\" }")
        // WHEN
        val exception = assertThrows(WebClientResponseException::class.java) { service.getProviderData(mockUrl) }
        // THEN
        assertThat("502 Bad Gateway from GET $mockUrl").isEqualTo(exception.message)
    }

    private fun getPreconfiguredMockWebServer(endpointUrl: String, status: Int, body: String): String {
        wireMock.stubFor(
            WireMock
                .get(endpointUrl)
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(status)
                        .withBody(body)
                )
        )
        return wireMock
            .baseUri
            .resolve(endpointUrl)
            .toString()
    }

}