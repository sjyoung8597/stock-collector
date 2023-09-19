package com.alring.stock.collector.service;

import com.alring.stock.collector.model.entity.MarketInfo;
import com.alring.stock.collector.model.stock.StockCrawlingInfo;
import com.alring.stock.collector.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    public void setMarket(List<StockCrawlingInfo> stockCrawlingInfoList) {
        stockCrawlingInfoList.stream()
                .map(StockCrawlingInfo::getMarketType)
                .distinct()
                .map(x -> new MarketInfo(0, x.getCountryType(), x, x.getDescription()))
                .forEach(marketRepository::insertMarket);
    }

    public List<MarketInfo> getMarketList() {
        return marketRepository.selectMarketList();
    }
}
