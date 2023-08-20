/*
 * This file is generated by jOOQ.
 */
package jooq.alring_dsl;


import java.util.Arrays;
import java.util.List;

import jooq.alring_dsl.tables.AlringCountry;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AlringStock extends SchemaImpl {

    private static final long serialVersionUID = -714127536;

    /**
     * The reference instance of <code>stock</code>
     */
    public static final AlringStock STOCK = new AlringStock();

    /**
     * The table <code>stock.country</code>.
     */
    public final AlringCountry COUNTRY = AlringCountry.COUNTRY;

    /**
     * No further instances allowed
     */
    private AlringStock() {
        super("stock", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            AlringCountry.COUNTRY);
    }
}
