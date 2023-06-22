package com.alring.stock.collector.config.db.model;

/**
 * Created by jysong@crossangle.io 2023-02-27
 */
public enum DatabaseModeType {
    NONE("none"),
    RDBMS("rdbms");
    private final String value;

    private DatabaseModeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
