CREATE TABLE country (
                         country_type VARCHAR(50) primary key NOT NULL,
                         description VARCHAR(1000) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

CREATE TABLE market (
                        market_no int(11) primary key AUTO_INCREMENT,
                        country_type VARCHAR(50) NOT NULL,
                        market_type VARCHAR(50) NOT NULL,
                        description VARCHAR(1000) NOT NULL,
                        INDEX country (country_type)
) ENGINE=INNODB DEFAULT CHARSET=utf8

CREATE TABLE exchange_rate (
                               exchange_no int(11) primary key AUTO_INCREMENT,
                               country_type VARCHAR(50) NOT NULL,
                               currency_type VARCHAR(50) NOT NULL,
                               ex_currency_type VARCHAR(50) NOT NULL,
                               currency_rate double(40,3) NOT NULL,
                               ex_currency_rate double(40,3) NOT NULL,
                               description VARCHAR(1000) NOT NULL,
                               create_date datetime NOT NULL,
                               INDEX date_index (create_date DESC, currency_type)
) ENGINE=INNODB DEFAULT CHARSET=utf8

CREATE TABLE stock (
                       stock_no int(11) primary key AUTO_INCREMENT,
                       stock_name VARCHAR(500) NOT NULL,
                       market_no int(11) NOT NULL,
                       stock_listed bigint(20) NOT NULL,
                       status VARCHAR(20) NOT NULL DEFAULT 'YES',
                       update_date datetime NOT NULL,
                       create_date datetime NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

CREATE TABLE stock_price_day (
                       price_no int(11) primary key AUTO_INCREMENT,
                       stock_no int(11) NOT NULL,
                       currency_type VARCHAR(50) NOT NULL,
                       price double(40,3) NOT NULL,
                       market_cap bigint(20) NOT NULL,
                       currency_rate double(40,3) NOT NULL,
                       create_date datetime NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

CREATE TABLE industry (
                          industry_no int(11) primary key AUTO_INCREMENT,
                          industry_type VARCHAR(50) NOT NULL,
                          description VARCHAR(1000) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

CREATE TABLE stock_industry (
                          stock_industry_no int(11) primary key AUTO_INCREMENT,
                          stock_no int(11) NOT NULL,
                          industry_no int(11) NOT NULL,
                          INDEX stock_index (stock_no),
                          INDEX industry_index (industry_no),
                          INDEX stock_industry_index (stock_no, industry_no)
) ENGINE=INNODB DEFAULT CHARSET=utf8

