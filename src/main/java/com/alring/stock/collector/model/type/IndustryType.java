package com.alring.stock.collector.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndustryType implements CodeEnum {
    ELECTRONIC("ELECTRONIC", "전자"),
    BIO("BIO", "생명공학"),
    BATTERY("BATTERY", "배터리"),
    IT("IT", "인터넷"),
    AUTOMOTIVE("AUTOMOTIVE", "자동차"),
    PETROCHEMICAL("PETROCHEMICAL", "석유화학"),
    FINANCE("FINANCE", "금융"),
    PHARMACEUTICAL("PHARMACEUTICAL", "제약"),
    CONGLOMERATE("CONGLOMERATE", "복합기업"),
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

    public static IndustryType getType(String type)
    {
        switch (type)
        {
            case "ELECTRONIC":
                return IndustryType.ELECTRONIC;
            case "BIO":
                return IndustryType.BIO;
            case "BATTERY":
                return IndustryType.BATTERY;
            default:
                return IndustryType.ELECTRONIC;
        }
    }

}
