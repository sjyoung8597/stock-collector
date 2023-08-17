package com.alring.stock.collector.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MarketType implements CodeEnum {
    KOSPI("KOSPI", "코스피"),
    KOSDAQ("KOSDAQ", "코스닥"),
    NASDAQ("NASDAQ", "나스닥"),
    NYSE("NYSE", "뉴욕증시"),
    AMEX("AMEX", "아멕스"),
    ;

    private String code;
    private String description;

    @Override
    public String getCode() {
        return code;
    }

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
