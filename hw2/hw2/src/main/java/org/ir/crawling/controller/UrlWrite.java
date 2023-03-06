package org.ir.crawling.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ir.crawling.model.CrawlStat;
import org.ir.crawling.model.FetchCrawlStat;
import org.ir.crawling.model.UrlCrawlStat;
import org.ir.crawling.model.enums.StatHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UrlWrite implements Write {

    private static final Logger logger = LoggerFactory.getLogger(UrlWrite.class);
    @Override
    public <T extends CrawlStat> void write(List<T> stat) {
        try {
            List<UrlCrawlStat> urlCrawlStats = new ArrayList<>();
            for (T crawlStat : stat) {
                urlCrawlStats.add((UrlCrawlStat) crawlStat);
            }
            BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(CrawlerController.URLS_LATIMES_CSV_OP));
            CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.INDICATOR.value);
            CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
            for (UrlCrawlStat urlStat : urlCrawlStats) {
                csvPrinter.printRecord(urlStat.getUrl(), urlStat.getSameDomainIndicator());
            }
            csvPrinter.flush();
        } catch (IOException exception) {
            logger.error("Error while writing to file: " + exception.getMessage());
        }
    }
}
