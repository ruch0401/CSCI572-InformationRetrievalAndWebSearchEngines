package org.ir.crawling.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ir.crawling.crawler.AlphaCrawler;
import org.ir.crawling.model.CrawlStat;
import org.ir.crawling.model.FetchCrawlStat;
import org.ir.crawling.model.enums.StatHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FetchWrite implements Write {

    private static final Logger logger = LoggerFactory.getLogger(FetchWrite.class);

    @Override
    public <T extends CrawlStat> void write(List<T> stat) {
        try {
            List<FetchCrawlStat> fetchCrawlStat = new ArrayList<>();
            for (T crawlStat : stat) {
                fetchCrawlStat.add((FetchCrawlStat) crawlStat);
            }
            BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(CrawlerController.FETCH_LATIMES_CSV_OP));
            CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.STATUS_CODE.value);
            CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
            for (FetchCrawlStat urlStatusCode : fetchCrawlStat) {
                csvPrinter.printRecord(urlStatusCode.getUrl(), urlStatusCode.getStatusCode());
            }
            csvPrinter.flush();
        } catch (IOException exception) {
            logger.error("Error while writing to file: " + exception.getMessage());
        }
    }
}
