package com.alring.stock.collector.service;

import com.alring.stock.collector.model.entity.CountryInfo;
import com.alring.stock.collector.model.stock.StockInfo;
import com.alring.stock.collector.model.type.CountryType;
import com.alring.stock.collector.repository.CountryRepository;
import com.alring.stock.collector.service.crawling.KospiCrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KospiService {

    private final KospiCrawlingService kospiCrawlingService;
    private final CountryRepository countryRepository;

    @Transactional
    public void setKospi() {
        List<StockInfo> stockInfoList = kospiCrawlingService.getKospi();

        List<CountryType> countryTypeList = stockInfoList.stream()
                .map(StockInfo::getCountryType)
                .filter(countryType ->
                        !countryRepository.selectCountryList().stream()
                                .map(CountryInfo::getCountryType)
                                .collect(Collectors.toSet())
                                .contains(countryType))
                .collect(Collectors.toList());

        countryTypeList.forEach(countryRepository::insertCountry);






    }
}
