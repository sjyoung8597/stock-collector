package com.alring.stock.collector.config.db.model;


import com.alring.stock.collector.model.PropertySourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by jysong@crossangle.io 2023-02-28
 */
@Slf4j
public class DatabaseAccessInfo {
    protected String host;
    protected String user;
    protected String password;

    public DatabaseAccessInfo(Map<String, String> map, String host) {
        this.host = (String)map.get(host);
        this.user = (String)map.get("uid");
        this.password = (String)map.get("pw");
        if (isEmpty(new String[]{this.host, this.user, this.password})) {
            throw new PropertySourceNotFoundException();
        }
    }

    public DatabaseAccessInfo(Map<String, String> map) {
        this.host = (String)map.get("host");
        this.user = (String)map.get("uid");
        this.password = (String)map.get("pw");
        if (isEmpty(new String[]{this.host, this.user, this.password})) {
            throw new PropertySourceNotFoundException();
        }
    }

    public String getHost() {
        return this.host;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isEmpty(String... values) {
        return Stream.of(values).anyMatch((str) -> {
            return str == null || "".equals(str);
        });
    }
}
