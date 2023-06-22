package com.alring.stock.collector.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KospiScheduler {

    @Scheduled(cron = "${kospi.scheduler.cron}")
    public void crawlingKospi() {
        System.out.println("kospi success");
    }
}
