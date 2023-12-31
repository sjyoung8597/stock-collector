/*
 * This file is generated by jOOQ.
 */
package jooq.alring_dsl.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AlringCountry implements Serializable {

    private static final long serialVersionUID = -107147522;

    private final String countryCode;
    private final String currencyCode;

    public AlringCountry(AlringCountry value) {
        this.countryCode = value.countryCode;
        this.currencyCode = value.currencyCode;
    }

    public AlringCountry(
        String countryCode,
        String currencyCode
    ) {
        this.countryCode = countryCode;
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AlringCountry (");

        sb.append(countryCode);
        sb.append(", ").append(currencyCode);

        sb.append(")");
        return sb.toString();
    }
}
