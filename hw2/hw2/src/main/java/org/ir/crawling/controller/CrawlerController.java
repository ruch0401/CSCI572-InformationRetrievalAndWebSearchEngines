package org.ir.crawling.controller;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ir.cli.Props;
import org.ir.crawling.crawler.AlphaCrawler;
import org.ir.crawling.model.FetchCrawlStat;
import org.ir.crawling.model.enums.StatHeader;
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

    static final Path FETCH_LATIMES_CSV_OP = Path.of(Props.OUTPUT_DIR, "fetch_latimes.csv");
    static final Path VISIT_LATIMES_CSV_OP = Path.of(Props.OUTPUT_DIR, "visit_latimes.csv");
    static final Path URLS_LATIMES_CSV_OP = Path.of(Props.OUTPUT_DIR, "urls_latimes.csv");

    static {
        try {
            if (Files.notExists(Props.ROOT_PATH)) {
                Files.createDirectory(Props.ROOT_PATH);
            }

            // ensuring that the output files do not exist previously. if they do, deleting and creating a new ones.
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
        configureCrawler(config);
        final var controller = instantiateCrawlerForThisCrawl(config);
        controller.addSeed(Props.SEED_URL);
        instantiateAndStartCrawling(controller);
        writeCrawlOutputsToFile();
    }

    private static void configureCrawler(CrawlConfig config) {
        config.setCrawlStorageFolder(Props.ROOT);
        config.setMaxDepthOfCrawling(Props.MAX_DEPTH);
        config.setMaxPagesToFetch(Props.MAX_PAGE_COUNT);
        config.setPolitenessDelay(Props.POLITENESS_DELAY_IN_MILLIS);
        config.setIncludeBinaryContentInCrawling(true);
    }

    private static CrawlController instantiateCrawlerForThisCrawl(CrawlConfig config) throws Exception {
        // Instantiate the controller for this crawl.
        final var pageFetcher = new PageFetcher(config);
        final var robotstxtConfig = new RobotstxtConfig();
        final var robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        final var controller = new CrawlController(config, pageFetcher, robotstxtServer);
        return controller;
    }

    private static void instantiateAndStartCrawling(CrawlController controller) {
        // instantiate and start crawling
        List<FetchCrawlStat> fetchCrawlStats = new ArrayList<>();
        List<VisitCrawlStat> visitCrawlStats = new ArrayList<>();
        List<UrlCrawlStat> urlCrawlStats = new ArrayList<>();
        CrawlController.WebCrawlerFactory<AlphaCrawler> factory = () -> new AlphaCrawler(fetchCrawlStats, visitCrawlStats, urlCrawlStats);

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.start(factory, Props.NUMBER_OF_CRAWLERS);
    }

    private static void writeCrawlOutputsToFile() throws IOException {
        FetchWrite fetchWrite = new FetchWrite();
        fetchWrite.write(AlphaCrawler.getFetchStats());

        VisitWrite visitWrite = new VisitWrite();
        visitWrite.write(AlphaCrawler.getVisitStats());

        UrlWrite urlWrite = new UrlWrite();
        urlWrite.write(AlphaCrawler.getUrlStats());
    }

//    private static void writeFetchStatsToCsv() throws IOException {
//        final var fetchStats = AlphaCrawler.getFetchStats();
//        BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(FETCH_LATIMES_CSV_OP));
//        CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.STATUS_CODE.value);
//        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
//        for (FetchCrawlStat urlStatusCode : fetchStats) {
//            csvPrinter.printRecord(urlStatusCode.getUrl(), urlStatusCode.getStatusCode());
//        }
//        csvPrinter.flush();
//    }

//    private static void writeVisitStatsToCsv() throws IOException {
//        final var visitStats = AlphaCrawler.getVisitStats();
//        BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(VISIT_LATIMES_CSV_OP));
//        CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.SIZE_OF_DOWNLOADED_PAGE.value, StatHeader.NUMBER_OF_OUTLINKS.value, StatHeader.CONTENT_TYPE.value);
//        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
//        for (VisitCrawlStat visitStat : visitStats) {
//            csvPrinter.printRecord(visitStat.getUrl(), visitStat.getDownloadedSize(), visitStat.getNumberOfOutlinks(), visitStat.getContentType());
//        }
//        csvPrinter.flush();
//    }

//    private static void writeUrlStatsToCsv() throws IOException {
//        final var urlStats = AlphaCrawler.getUrlStats();
//        BufferedWriter bufferedWriter = new BufferedWriter(Files.newBufferedWriter(URLS_LATIMES_CSV_OP));
//        CSVFormat format = CSVFormat.DEFAULT.withHeader(StatHeader.URL.value, StatHeader.INDICATOR.value);
//        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, format);
//        for (UrlCrawlStat urlStat : urlStats) {
//            csvPrinter.printRecord(urlStat.getUrl(), urlStat.getSameDomainIndicator());
//        }
//        csvPrinter.flush();
//    }
}