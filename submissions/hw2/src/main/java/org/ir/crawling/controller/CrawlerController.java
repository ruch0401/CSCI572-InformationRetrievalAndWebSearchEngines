package org.ir.crawling.controller;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ir.crawling.crawler.AlphaCrawler;
import org.ir.crawling.model.FetchCrawlStat;
import org.ir.crawling.model.StatHeader;
import org.ir.crawling.model.UrlCrawlStat;
import org.ir.crawling.model.VisitCrawlStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CrawlerController {
    private static final String ROOT = "../data/crawl/root";
    public static final String OUTPUT_DIR = "output";
    public static final int NUMBER_OF_CRAWLERS = 30;
    public static final String SEED_URL = "https://www.latimes.com/";
    public static final int MAX_DEPTH = 16;
    public static final int MAX_PAGE_COUNT = 20000;
    public static final int POLITENESS_DELAY_IN_MILLIS = 1;
    private static final Path FETCH_LATIMES_CSV_OP = Path.of(CrawlerController.OUTPUT_DIR, "fetch_latimes.csv");
    private static final Path VISIT_LATIMES_CSV_OP = Path.of(CrawlerController.OUTPUT_DIR, "visit_latimes.csv");
    private static final Path URLS_LATIMES_CSV_OP = Path.of(CrawlerController.OUTPUT_DIR, "urls_latimes.csv");
    private static final Path ROOT_PATH = Path.of(ROOT);

    static {
        try {
            if (Files.notExists(ROOT_PATH)) {
                Files.createDirectory(ROOT_PATH);
            }

            // ensuring that the file does not exist previously. if it does, deleting it and creating a new one.
            Files.deleteIfExists(FETCH_LATIMES_CSV_OP);
            Files.deleteIfExists(VISIT_LATIMES_CSV_OP);
            Files.deleteIfExists(URLS_LATIMES_CSV_OP);

            Files.createFile(FETCH_LATIMES_CSV_OP);
            Files.createFile(VISIT_LATIMES_CSV_OP);
            Files.createFile(URLS_LATIMES_CSV_OP);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    public static void main(String[] args) throws Exception {

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(ROOT);
        config.setMaxDepthOfCrawling(MAX_DEPTH);
        config.setMaxPagesToFetch(MAX_PAGE_COUNT);
        config.setPolitenessDelay(POLITENESS_DELAY_IN_MILLIS);
        config.setIncludeBinaryContentInCrawling(true);

        // Instantiate the controller for this crawl.
        final var pageFetcher = new PageFetcher(config);
        final var robotstxtConfig = new RobotstxtConfig();
        final var robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        final var controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        controller.addSeed(SEED_URL);

        List<FetchCrawlStat> fetchCrawlStats = new ArrayList<>();
        List<VisitCrawlStat> visitCrawlStats = new ArrayList<>();
        List<UrlCrawlStat> urlCrawlStats = new ArrayList<>();

        // The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<AlphaCrawler> factory = () -> new AlphaCrawler(fetchCrawlStats, visitCrawlStats, urlCrawlStats);

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.start(factory, NUMBER_OF_CRAWLERS);

        writeFetchStatsToCsv();
        writeVisitStatsToCsv();
        writeUrlStatsToCsv();
    }

    private static void writeFetchStatsToCsv() throws IOException {
        final var fetchStats = AlphaCrawler.getFetchStats();
        BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(FETCH_LATIMES_CSV_OP));
        CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.STATUS_CODE.value);
        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
        for (FetchCrawlStat urlStatusCode : fetchStats) {
            csvPrinter.printRecord(urlStatusCode.getUrl(), urlStatusCode.getStatusCode());
        }
        csvPrinter.flush();
    }

    private static void writeVisitStatsToCsv() throws IOException {
        final var visitStats = AlphaCrawler.getVisitStats();
        BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(VISIT_LATIMES_CSV_OP));
        CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.SIZE_OF_DOWNLOADED_PAGE.value, StatHeader.NUMBER_OF_OUTLINKS.value, StatHeader.CONTENT_TYPE.value);
        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
        for (VisitCrawlStat visitStat : visitStats) {
            csvPrinter.printRecord(visitStat.getUrl(), visitStat.getDownloadedSize(), visitStat.getNumberOfOutlinks(), visitStat.getContentType());
        }
        csvPrinter.flush();
    }

    private static void writeUrlStatsToCsv() throws IOException {
        final var urlStats = AlphaCrawler.getUrlStats();
        BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(URLS_LATIMES_CSV_OP));
        CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.INDICATOR.value);
        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
        for (UrlCrawlStat urlStat : urlStats) {
            csvPrinter.printRecord(urlStat.getUrl(), urlStat.getSameDomainIndicator());
        }
        csvPrinter.flush();
    }
}