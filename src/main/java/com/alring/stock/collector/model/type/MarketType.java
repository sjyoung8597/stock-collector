package com.alring.stock.collector.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MarketType implements CodeEnum {
    KOSPI("KOSPI", CountryType.KR, "코스피"),
    KOSDAQ("KOSDAQ", CountryType.KR, "코스닥"),
    NASDAQ("NASDAQ", CountryType.USA, "나스닥"),
    NYSE("NYSE", CountryType.USA, "뉴욕증시"),
    AMEX("AMEX", CountryType.USA, "아멕스"),
    ;

    private String code;
    private CountryType countryType;
    private String description;

    @Override
    public String getCode() {
        return code;
    }

    public CountryType getCountryType() { return countryType; }

    public String getDescription() {
        return description;
    }

    public static MarketType getType(String type)
    {
        switch (type)
        {
            case "KOSPI":
                return MarketType.KOSPI;
            case "KOSDAQ":
                return MarketType.KOSDAQ;
            case "NASDAQ":
                return MarketType.NASDAQ;
            case "NYSE":
                return MarketType.NYSE;
            case "AMEX":
                return MarketType.AMEX;
            default:
                return MarketType.KOSPI;
        }
    }

}
