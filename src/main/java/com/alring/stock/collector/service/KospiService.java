package com.alring.stock.collector.service;

import com.alring.stock.collector.model.entity.CountryInfo;
import com.alring.stock.collector.model.stock.StockCrawlingInfo;
import com.alring.stock.collector.model.type.CountryType;
import com.alring.stock.collector.repository.CountryRepository;
import com.alring.stock.collector.service.crawling.KospiCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KospiService {

    private final KospiCrawlingService kospiCrawlingService;
    private final CountryRepository countryRepository;

    @Transactional
    public void setKospi() {
        List<StockCrawlingInfo> stockInfoList = kospiCrawlingService.getKospi();
        List<CountryInfo> countryInfoList = countryRepository.selectCountryList();

        List<CountryType> countryTypeList = stockInfoList.stream()
                .map(StockCrawlingInfo::getCountryType)
                .filter(countryType ->
                        !countryInfoList.stream()
                                .map(CountryInfo::getCountryType)
                                .collect(Collectors.toSet())
                                .contains(countryType))
                .collect(Collectors.toList());

        countryTypeList.forEach(countryRepository::insertCountry);






    }
}
