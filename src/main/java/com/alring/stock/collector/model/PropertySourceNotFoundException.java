package com.alring.stock.collector.model;


public class PropertySourceNotFoundException extends RuntimeException {
    public PropertySourceNotFoundException() {
        super("Cannot get properties from redis. Check if redis stores all the information required");
    }
}
