package com.alring.stock.collector.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusType implements CodeEnum {
    YES("YES"),
    NO("NO"),
    ;

    private String code;

    @Override
    public String getCode() {
        return code;
    }


}
