package com.alring.stock.collector.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountryType implements CodeEnum {
    KR("KR", "korea"),
    USA("USA", "usa"),
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

    public static CountryType getType(String type)
    {
        switch(type)
        {
            case "KR":
                return CountryType.KR;
            default:
                return CountryType.KR;
        }
    }
}
