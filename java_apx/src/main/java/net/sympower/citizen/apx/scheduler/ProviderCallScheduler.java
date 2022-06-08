package net.sympower.citizen.apx.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class ProviderCallScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderCallScheduler.class);

    @Scheduled(cron = "${cron.every-hour}")
    public void callProvider() {
        LOGGER.info("Execute Provider caller: ${LocalDateTime.now()}");
        LOGGER.warn("I'm doing nothing with data like this: ${dataResponseService.getDataResponse()}");
    }
}
