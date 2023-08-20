package com.alring.stock.collector.config.jooq;

import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.tools.LoggerListener;
import org.jooq.tools.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import java.time.Duration;
import java.util.Arrays;



public class BaseJooqExecuteListener extends DefaultExecuteListener {

    private static final long SLOW_QUERY_NANO_TIME = 5_000_000_000L;
    private static final Logger log = LoggerFactory.getLogger(LoggerListener.class);

    StopWatch watch;

    @Override
    public void executeStart(ExecuteContext ctx) {
        super.executeStart(ctx);
        watch = new StopWatch();
        // Create a new DSLContext for logging rendering purposes
        // This DSLContext doesn't need a connection, only the SQLDialect...
        DSLContext create = DSL.using(ctx.dialect(),
                // ... and the flag for pretty-printing
                new Settings().withRenderFormatted(true));
        // If we're executing a query
        if (ctx.query() != null) {
            System.out.println(create.renderInlined(ctx.query()));
        } else if (ctx.routine() != null) {
            // If we're executing a routine
            System.out.println(create.renderInlined(ctx.routine()));
        } else if (ctx.batchQueries() != null) {
            // If we're executing a batch queries
            Arrays.stream(ctx.batchQueries()).forEach(query -> System.out.println(create.renderInlined(query)));
        }
    }

    @Override
    public void executeEnd(ExecuteContext ctx) {
        super.executeEnd(ctx);
        final long queryExecutedTime = watch.split();

        if (queryExecutedTime > SLOW_QUERY_NANO_TIME) {
            log.debug("[alring] Slow Query Detected \n {}", ctx.sql(),
                    String.format("querys : %d ms , executed : %d ms", Duration.ofNanos(SLOW_QUERY_NANO_TIME), Duration.ofNanos(queryExecutedTime))
            );
        }
    }

    @Override
    public void exception(ExecuteContext context) {
        SQLDialect dialect = context.configuration().dialect();
        SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dialect.thirdParty().springDbName());

        context.exception(translator.translate("Access database using jOOQ", context.sql(), context.sqlException()));
    }
}
