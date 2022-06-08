package net.sympower.citizen.apx;

import com.github.tomakehurst.wiremock.client.WireMock;
import de.mkammerer.wiremock.WireMockExtension;
import net.sympower.citizen.apx.dto.incomming.Quote;
import net.sympower.citizen.apx.dto.incomming.QuoteResponse;
import net.sympower.citizen.apx.service.DataRequestService;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
class DataRequestServiceTest {

    @RegisterExtension
    static final WireMockExtension wireMock = new WireMockExtension();

    @Autowired
    private DataRequestService service;

    @Value("classpath:apx-data.json")
    private Resource apxData;

    @Test
    void shouldGetResponseForMockedProvider() throws IOException {
        // GIVEN
        String jsonString = FileUtils.readFileToString(apxData.getFile());
        String mockUrl = getPreconfiguredMockWebServerUrl("/some/endpoint", HttpStatus.OK_200, jsonString);
        // WHEN
        QuoteResponse response = service.getProviderData(mockUrl);
        // THEN
        assertThat(response.getQuote()).hasSize(24);
        Quote quote = response.getQuote().get(4);
        assertThat(quote.getMarket()).isEqualTo("APX Power NL Hourly");
        assertThat(quote.getDate_applied()).isEqualTo(1573599600000L);
        assertThat(quote.getValues()).hasSize(4);
        assertThat(quote.getValues().get(0).gettLabel()).isEqualTo("Order");
        assertThat(quote.getValues().get(0).getValue()).isEqualTo("5");
        assertThat(quote.getValues().get(1).gettLabel()).isEqualTo("Hour");
        assertThat(quote.getValues().get(1).getValue()).isEqualTo("05");
        assertThat(quote.getValues().get(2).gettLabel()).isEqualTo("Net Volume");
        assertThat(quote.getValues().get(2).getValue()).isEqualTo("4838.70");
        assertThat(quote.getValues().get(3).gettLabel()).isEqualTo("Price");
        assertThat(quote.getValues().get(3).getValue()).isEqualTo("34.72");
        quote = response.getQuote().get(18);
        assertThat(quote.getMarket()).isEqualTo("APX Power NL Hourly");
        assertThat(quote.getDate_applied()).isEqualTo(1573599600000L);
        assertThat(quote.getValues()).hasSize(4);
        assertThat(quote.getValues().get(0).gettLabel()).isEqualTo("Order");
        assertThat(quote.getValues().get(0).getValue()).isEqualTo("19");
        assertThat(quote.getValues().get(1).gettLabel()).isEqualTo("Hour");
        assertThat(quote.getValues().get(1).getValue()).isEqualTo("19");
        assertThat(quote.getValues().get(2).gettLabel()).isEqualTo("Net Volume");
        assertThat(quote.getValues().get(2).getValue()).isEqualTo("3384.30");
        assertThat(quote.getValues().get(3).gettLabel()).isEqualTo("Price");
        assertThat(quote.getValues().get(3).getValue()).isEqualTo("56.09");
    }

    @Test
    void shouldThrowExceptionByProviderIssues() {
        // GIVEN
        String mockUrl = getPreconfiguredMockWebServerUrl("/broken/endpoint", HttpStatus.BAD_GATEWAY_502, "{ \"error\": \"BOOOOOM!\" }");
        // WHEN
        WebClientResponseException exception = assertThrows(WebClientResponseException.class, () -> service.getProviderData(mockUrl));
        // THEN
        assertThat(String.format("502 Bad Gateway from GET %s", mockUrl)).isEqualTo(exception.getMessage());
    }


    private String getPreconfiguredMockWebServerUrl(String endpointUrl, int status, String body) {
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
        );
        return wireMock
                .getBaseUri()
                .resolve(endpointUrl)
                .toString();
    }

}
