package net.sympower.citizen.apx.scheduler

import net.sympower.citizen.apx.service.DataResponseService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime

class ProviderCallScheduler(
    private val dataResponseService: DataResponseService
) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ProviderCallScheduler::class.java)
    }

    @Scheduled(cron = "\${cron.every-hour}")
    fun callProvider() {
        LOGGER.info("Execute Provider caller: ${LocalDateTime.now()}")
        LOGGER.warn("I'm doing nothing with data like this: ${dataResponseService.getDataResponse()}")
    }

}