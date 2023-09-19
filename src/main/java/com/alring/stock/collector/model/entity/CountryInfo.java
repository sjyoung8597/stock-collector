package com.alring.stock.collector.model.entity;

import com.alring.stock.collector.model.type.CountryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CountryInfo implements Serializable {
    private static final long serialVersionUID = -493894612469280001L;

    private CountryType countryType;
    private String description;

    public CountryInfo() {

    }
}
