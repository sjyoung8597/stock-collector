package com.alring.stock.collector.model.entity;

import com.alring.stock.collector.model.type.CountryType;
import com.alring.stock.collector.model.type.MarketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by jysong@crossangle.io 2023/08/21
 */

@Getter
@Setter
@AllArgsConstructor
public class MarketInfo implements Serializable {

    private int marketNo;
    private CountryType countryType;
    private MarketType marketType;
    private String description;

    public MarketInfo() {}
}
