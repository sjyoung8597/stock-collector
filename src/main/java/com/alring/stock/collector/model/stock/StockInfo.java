package com.alring.stock.collector.model.stock;

import com.alring.stock.collector.model.type.CountryType;
import com.alring.stock.collector.model.type.CurrencyType;
import com.alring.stock.collector.model.type.IndustryType;
import com.alring.stock.collector.model.type.MarketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockInfo {

    public int stockNo;
    public CountryType countryType;
    public String stockName;
    public CurrencyType currencyType;
    public IndustryType industryType;
    public MarketType marketType;
    public double price;
    public long stockListed;
    public long marketCap;
}
