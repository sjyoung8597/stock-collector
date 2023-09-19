package com.alring.stock.collector.model.entity;

import com.alring.stock.collector.model.type.CountryType;
import com.alring.stock.collector.model.type.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRateInfo {
    private int exchangeNo;
    private CountryType countryType;
    private CurrencyType currencyType;
    private CurrencyType exCurrencyType;
    private double currencyRate;
    private double exCurrencyRate;
    private String description;
    private long createDate;

    public ExchangeRateInfo() {}
}
