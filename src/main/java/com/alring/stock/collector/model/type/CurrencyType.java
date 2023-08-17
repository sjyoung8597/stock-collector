package com.alring.stock.collector.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType implements CodeEnum {
    KRW("KRW", "won"),
    USD("USD", "USD"),
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

    public static CurrencyType getType(String type)
    {
        switch (type)
        {
            case "KRW":
                return CurrencyType.KRW;
            case "USD":
                return CurrencyType.USD;
            default:
                return CurrencyType.KRW;
        }
    }

}
