package com.alring.stock.collector.scheduler;

import com.alring.stock.collector.model.stock.StockInfo;
import com.alring.stock.collector.service.crawling.KospiCrawlingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class KospiScheduler {

    private final KospiCrawlingService kospiCrawlingService;

    @Scheduled(cron = "${kospi.scheduler.cron}")
    public void crawlingKospi() {
        List<StockInfo> stockInfoList = kospiCrawlingService.getKospi();
        System.out.println("kospi success");
    }
}
