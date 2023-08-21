package com.alring.stock.collector.model.entity;

import com.alring.stock.collector.model.type.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockInfo {
    private int stockNo;
    private String stockName;
    private int marketNo;
    private long stockLited;
    private StatusType status;
    private long updateDate;
    private long createDate;
}
