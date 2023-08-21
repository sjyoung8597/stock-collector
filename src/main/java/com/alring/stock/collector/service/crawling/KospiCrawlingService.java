package com.alring.stock.collector.service.crawling;

import com.alring.stock.collector.model.stock.StockCrawlingInfo;
import com.alring.stock.collector.model.type.CountryType;
import com.alring.stock.collector.model.type.CurrencyType;
import com.alring.stock.collector.model.type.IndustryType;
import com.alring.stock.collector.model.type.MarketType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class KospiCrawlingService {

    private static final String KOSPI_URL = "https://finance.naver.com/sise/sise_market_sum.nhn?sosok=0&page=";
    private static int END_PAGE = 50;

    public List<StockCrawlingInfo> getKospi() {
        List<StockCrawlingInfo> stockInfoList = new ArrayList<>();
        try {
            for (int page = 1; page <= END_PAGE; page++) {
                boolean check = true;
                Document doc = Jsoup.connect(KOSPI_URL + String.valueOf(page)).get();
                int endSize = doc.select("tbody").get(1).select("td").size();

                int splitIndex = 1;

                for (int currentIndex = 0; splitIndex < 51;) {
                    int firstIndex = 2;
                    int priceIndex = 1;
                    int marketCapIndex = 5;
                    int stockListedIndex = 6;

                    if (currentIndex + firstIndex + stockListedIndex < endSize) {
                        if (!(doc.select("tbody").get(1).select("td").get(currentIndex + firstIndex).text().replace(",", "").equals("")
                                || doc.select("tbody").get(1).select("td").get(currentIndex + firstIndex + priceIndex).text().replace(",", "").equals("")
                                || doc.select("tbody").get(1).select("td").get(currentIndex + firstIndex + stockListedIndex).text().replace(",", "").equals(""))) {
                            String stockName = doc.select("tbody").get(1).select("td").get(currentIndex + firstIndex).text();
                            double price = Double.valueOf(doc.select("tbody").get(1).select("td").get(currentIndex + firstIndex + priceIndex).text().replace(",", ""));
                            long stockListed = Long.valueOf(doc.select("tbody").get(1).select("td").get(currentIndex + firstIndex + stockListedIndex).text().replace(",", "")) * 1000;
                            long marketCap = (long) (price * stockListed);
                            CountryType countryType = CountryType.KR;
                            CurrencyType currencyType = CurrencyType.KRW;
                            MarketType marketType = MarketType.KOSPI;
                            IndustryType industryType = IndustryType.ELECTRONIC;
                            StockCrawlingInfo stockInfo = new StockCrawlingInfo(0, countryType, stockName, currencyType, industryType, marketType, price, stockListed, marketCap);
                            stockInfoList.add(stockInfo);
                        }
                        currentIndex += 13;
                        if (splitIndex % 5 == 0) {
                            currentIndex += 3;
                        }
                    }
                    splitIndex++;
                }
            }

        } catch(IOException exception) {

        }
        return stockInfoList;
    }
}
