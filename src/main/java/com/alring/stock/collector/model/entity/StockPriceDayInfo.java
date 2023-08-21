package com.alring.stock.collector.model.entity;

import com.alring.stock.collector.model.type.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockPriceDayInfo {
    private int priceNo;
    private int stockNo;
    private CurrencyType currencyType;
    private double price;
    private long marketCap;
    private double currencyRate;
    private long createDate;
}
