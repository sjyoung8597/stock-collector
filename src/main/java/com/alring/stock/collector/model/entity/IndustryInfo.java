package com.alring.stock.collector.model.entity;

import com.alring.stock.collector.model.type.IndustryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IndustryInfo {
    private int industryNo;
    private IndustryType industryType;
    private String description;

    public IndustryInfo() {}
}
