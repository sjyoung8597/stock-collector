package com.alring.stock.collector.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockIndustryInfo {
    private int stockIndustryNo;
    private int stockNo;
    private int industryNo;

    public StockIndustryInfo() {}
}
