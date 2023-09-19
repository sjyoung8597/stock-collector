package com.alring.stock.collector.service.kospi;

import com.alring.stock.collector.model.stock.StockCrawlingInfo;
import com.alring.stock.collector.service.CountryService;
import com.alring.stock.collector.service.MarketService;
import com.alring.stock.collector.service.crawling.KospiCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KospiService {

    private final KospiCrawlingService kospiCrawlingService;
    private final CountryService countryService;
    private final MarketService marketService;


    @Transactional
    public void setKospi() {
        List<StockCrawlingInfo> stockCrawlingInfoList = kospiCrawlingService.getKospi();
        countryService.setCountry(stockCrawlingInfoList);


        marketService.setMarket(stockCrawlingInfoList);

    }
}
