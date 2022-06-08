package net.sympower.citizen.apx;

import net.sympower.citizen.apx.config.ProviderEndpointsProperty;
import net.sympower.citizen.apx.dto.outgoing.DataResponse;
import net.sympower.citizen.apx.service.DataRequestService;
import net.sympower.citizen.apx.service.DataResponseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DataResponseServiceTest {

    public DataRequestService mockDataRequestService = mock(DataRequestService.class);
    public ProviderEndpointsProperty mockProviderEndpoints = mock(ProviderEndpointsProperty.class);
    public DataResponseService dataResponseService = new DataResponseService(mockDataRequestService, mockProviderEndpoints);

    @BeforeEach
    void setUp() {
        when(mockProviderEndpoints.getSomeProviderGet()).thenReturn("SOME_MOCK_URL");
    }

    @Test
    void shouldGetDataResponseForTheSameQuotes() {
        // GIVEN
        when(mockDataRequestService.getProviderData(any())).thenReturn(MockData.getSameQuotes());
        // WHEN
        List<DataResponse> response = dataResponseService.getDataResponse();
        // THEN
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getMarket()).isEqualTo("SomeMarket");
        assertThat(response.get(0).getDate()).isEqualTo(1573599600000L);
        assertThat(response.get(0).getValues()).hasSize(2);
        assertThat(response.get(0).getValues().get(0).getHour()).isEqualTo(1);
        assertThat(response.get(0).getValues().get(0).getNetVolume()).isEqualTo(BigDecimal.valueOf(11.11));
        assertThat(response.get(0).getValues().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(11.22));
        assertThat(response.get(0).getValues().get(1).getHour()).isEqualTo(2);
        assertThat(response.get(0).getValues().get(1).getNetVolume()).isEqualTo(BigDecimal.valueOf(22.11));
        assertThat(response.get(0).getValues().get(1).getPrice()).isEqualTo(BigDecimal.valueOf(22.22));
    }

    @Test
    void shouldGetDataResponseForDifferentQuotes() {
        // GIVEN
        when(mockDataRequestService.getProviderData(any())).thenReturn(MockData.getDifferentQuotes());
        // WHEN
        List<DataResponse> response = dataResponseService.getDataResponse();
        // THEN
        assertThat(response).hasSize(2);
        assertThat(response.get(0).getMarket()).isEqualTo("SomeMarket");
        assertThat(response.get(0).getDate()).isEqualTo(1573599600000L);
        assertThat(response.get(0).getValues()).hasSize(1);
        assertThat(response.get(0).getValues().get(0).getHour()).isEqualTo(1);
        assertThat(response.get(0).getValues().get(0).getNetVolume()).isEqualTo(BigDecimal.valueOf(11.11));
        assertThat(response.get(0).getValues().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(11.22));
        assertThat(response.get(1).getMarket()).isEqualTo("OtherMarket");
        assertThat(response.get(1).getDate()).isEqualTo(1573599600000L);
        assertThat(response.get(1).getValues()).hasSize(1);
        assertThat(response.get(1).getValues().get(0).getHour()).isEqualTo(3);
        assertThat(response.get(1).getValues().get(0).getNetVolume()).isEqualTo(BigDecimal.valueOf(33.11));
        assertThat(response.get(1).getValues().get(0).getPrice()).isEqualTo(BigDecimal.valueOf(33.22));
    }

}
