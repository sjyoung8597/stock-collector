package com.alring.stock.collector.service;

import com.alring.stock.collector.model.entity.CountryInfo;
import com.alring.stock.collector.model.type.CountryType;
import com.alring.stock.collector.repository.CountryRepository;
import com.alring.stock.collector.service.crawling.KospiCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KospiService {

    private final KospiCrawlingService kospiCrawlingService;
    private final CountryRepository countryRepository;

    @Transactional
    public void setKospi() {
//        List<StockInfo> stockInfoList = kospiCrawlingService.getKospi();
//        List<CountryType> countryTypeList = stockInfoList.stream().map(StockInfo::getCountryType).distinct().collect(Collectors.toList());
//
//        countryTypeList.stream()
//                .forEach(x -> countryRepository.insertCountry(new CountryInfo(x, "test")));
        CountryInfo test = countryRepository.selectCountry(CountryType.KR);
        int a = 0;
    }
}
