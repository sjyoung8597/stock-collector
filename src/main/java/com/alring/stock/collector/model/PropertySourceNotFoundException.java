package com.alring.stock.collector.model;

/**
 * Created by jysong@crossangle.io 2023/03/02
 */
public class PropertySourceNotFoundException extends RuntimeException {
    public PropertySourceNotFoundException() {
        super("Cannot get properties from redis. Check if redis stores all the information required");
    }
}
