package com.alring.stock.collector.model.entity;

import com.alring.stock.collector.model.type.CountryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CountryInfo {
    private CountryType countryType;
    private String description;
}
