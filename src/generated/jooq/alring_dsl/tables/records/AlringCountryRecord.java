/*
 * This file is generated by jOOQ.
 */
package jooq.alring_dsl.tables.records;


import jooq.alring_dsl.tables.AlringCountry;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AlringCountryRecord extends UpdatableRecordImpl<AlringCountryRecord> implements Record2<String, String> {

    private static final long serialVersionUID = 1989365693;

    /**
     * Setter for <code>stock.country.country_code</code>. 나라 코드
     */
    public AlringCountryRecord setCountryCode(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>stock.country.country_code</code>. 나라 코드
     */
    public String getCountryCode() {
        return (String) get(0);
    }

    /**
     * Setter for <code>stock.country.currency_code</code>. 통화
     */
    public AlringCountryRecord setCurrencyCode(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>stock.country.currency_code</code>. 통화
     */
    public String getCurrencyCode() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return AlringCountry.COUNTRY.COUNTRY_CODE;
    }

    @Override
    public Field<String> field2() {
        return AlringCountry.COUNTRY.CURRENCY_CODE;
    }

    @Override
    public String component1() {
        return getCountryCode();
    }

    @Override
    public String component2() {
        return getCurrencyCode();
    }

    @Override
    public String value1() {
        return getCountryCode();
    }

    @Override
    public String value2() {
        return getCurrencyCode();
    }

    @Override
    public AlringCountryRecord value1(String value) {
        setCountryCode(value);
        return this;
    }

    @Override
    public AlringCountryRecord value2(String value) {
        setCurrencyCode(value);
        return this;
    }

    @Override
    public AlringCountryRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AlringCountryRecord
     */
    public AlringCountryRecord() {
        super(AlringCountry.COUNTRY);
    }

    /**
     * Create a detached, initialised AlringCountryRecord
     */
    public AlringCountryRecord(String countryCode, String currencyCode) {
        super(AlringCountry.COUNTRY);

        set(0, countryCode);
        set(1, currencyCode);
    }
}
