package net.sympower.citizen.apx

import io.mockk.every
import io.mockk.mockk
import net.sympower.citizen.apx.config.ProviderEndpointsProperty
import net.sympower.citizen.apx.service.DataRequestService
import net.sympower.citizen.apx.service.DataResponseService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DataResponseServiceTest {

    private val mockDataRequestService = mockk<DataRequestService>()
    private val mockProviderEndpoints = mockk<ProviderEndpointsProperty>()
    private val dataResponseService = DataResponseService(mockDataRequestService,mockProviderEndpoints)


    @BeforeEach
    fun setUp() {
        every { mockProviderEndpoints.someProviderGet } returns "SOME_MOCK_URL"
    }

    @Test
    fun `should get DataResponse for the same quotes`() {
        // GIVEN
        every { mockDataRequestService.getProviderData(any()) } returns MockData.getSameQuotes()
        // WHEN
        val response = dataResponseService.getDataResponse()
        // THEN
        assertThat(response).hasSize(1)
        assertThat(response[0].market).isEqualTo("SomeMarket")
        assertThat(response[0].date).isEqualTo(1573599600000)
        assertThat(response[0].values).hasSize(2)
        assertThat(response[0].values[0].hour).isEqualTo(1)
        assertThat(response[0].values[0].netVolume).isEqualTo(BigDecimal.valueOf(11.11))
        assertThat(response[0].values[0].price).isEqualTo(BigDecimal.valueOf(11.22))
        assertThat(response[0].values[1].hour).isEqualTo(2)
        assertThat(response[0].values[1].netVolume).isEqualTo(BigDecimal.valueOf(22.11))
        assertThat(response[0].values[1].price).isEqualTo(BigDecimal.valueOf(22.22))
    }

    @Test
    fun `should get DataResponse for different quotes`() {
        // GIVEN
        every { mockDataRequestService.getProviderData(any()) } returns MockData.getDifferentQuotes()
        // WHEN
        val response = dataResponseService.getDataResponse()
        // THEN
        println("finish")
        assertThat(response).hasSize(2)
        assertThat(response[0].market).isEqualTo("SomeMarket")
        assertThat(response[0].date).isEqualTo(1573599600000)
        assertThat(response[0].values).hasSize(1)
        assertThat(response[0].values[0].hour).isEqualTo(1)
        assertThat(response[0].values[0].netVolume).isEqualTo(BigDecimal.valueOf(11.11))
        assertThat(response[0].values[0].price).isEqualTo(BigDecimal.valueOf(11.22))
        assertThat(response[1].market).isEqualTo("OtherMarket")
        assertThat(response[1].date).isEqualTo(1573599600000)
        assertThat(response[1].values).hasSize(1)
        assertThat(response[1].values[0].hour).isEqualTo(3)
        assertThat(response[1].values[0].netVolume).isEqualTo(BigDecimal.valueOf(33.11))
        assertThat(response[1].values[0].price).isEqualTo(BigDecimal.valueOf(33.22))
    }

}