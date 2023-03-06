package org.ir.crawling.controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ir.crawling.model.CrawlStat;
import org.ir.crawling.model.FetchCrawlStat;
import org.ir.crawling.model.VisitCrawlStat;
import org.ir.crawling.model.enums.StatHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class VisitWrite implements Write {

    private static final Logger logger = LoggerFactory.getLogger(VisitWrite.class);
    @Override
    public <T extends CrawlStat> void write(List<T> stat) {
        try {
            List<VisitCrawlStat> visitCrawlStats = new ArrayList<>();
            for (T crawlStat : stat) {
                visitCrawlStats.add((VisitCrawlStat) crawlStat);
            }
            BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(CrawlerController.VISIT_LATIMES_CSV_OP));
            CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.SIZE_OF_DOWNLOADED_PAGE.value, StatHeader.NUMBER_OF_OUTLINKS.value, StatHeader.CONTENT_TYPE.value);
            CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
            for (VisitCrawlStat visitStat : visitCrawlStats) {
                csvPrinter.printRecord(visitStat.getUrl(), visitStat.getDownloadedSize(), visitStat.getNumberOfOutlinks(), visitStat.getContentType());
            }
            csvPrinter.flush();
        } catch (IOException exception) {
            logger.error("Error while writing to file: " + exception.getMessage());
        }
    }
}
