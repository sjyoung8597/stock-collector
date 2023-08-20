package com.alring.stock.collector.scheduler;

import com.alring.stock.collector.service.KospiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KospiScheduler {

    private final KospiService kospiService;

    @Scheduled(cron = "${kospi.scheduler.cron}")
    public void crawlingKospi() {
        kospiService.setKospi();
        System.out.println("kospi success");
    }
}
