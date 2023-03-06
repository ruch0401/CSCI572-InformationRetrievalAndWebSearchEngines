package org.ir.crawling.controller;

import com.beust.jcommander.JCommander;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.ir.cli.Props;
import org.ir.crawling.crawler.AlphaCrawler;
import org.ir.crawling.model.FetchCrawlStat;
import org.ir.crawling.model.UrlCrawlStat;
import org.ir.crawling.model.VisitCrawlStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CrawlerController {
    
    static final Props props = Props.getInstance();
    static final Path FETCH_LATIMES_CSV_OP = Path.of(props.OUTPUT_DIR, "fetch_latimes.csv");
    static final Path VISIT_LATIMES_CSV_OP = Path.of(props.OUTPUT_DIR, "visit_latimes.csv");
    static final Path URLS_LATIMES_CSV_OP = Path.of(props.OUTPUT_DIR, "urls_latimes.csv");

    static {
        try {
            if (Files.notExists(props.ROOT_PATH)) {
                Files.createDirectory(props.ROOT_PATH);
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
        
        JCommander.newBuilder()
                .addObject(props)
                .build()
                .parse(args);

        CrawlConfig config = new CrawlConfig();
        configureCrawler(config);
        final var controller = instantiateCrawlerForThisCrawl(config);
        controller.addSeed(props.SEED_URL_HTTPS);
        instantiateAndStartCrawling(controller);
        writeCrawlOutputsToFile();
    }

    private static void configureCrawler(CrawlConfig config) {
        config.setCrawlStorageFolder(props.ROOT);
        config.setMaxDepthOfCrawling(props.MAX_DEPTH);
        config.setMaxPagesToFetch(props.MAX_PAGE_COUNT);
        config.setPolitenessDelay(props.POLITENESS_DELAY_IN_MILLIS);
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
        controller.start(factory, props.NUMBER_OF_CRAWLERS);
    }

    private static void writeCrawlOutputsToFile() throws IOException {
        FetchWrite fetchWrite = new FetchWrite();
        fetchWrite.write(AlphaCrawler.getFetchStats());

        VisitWrite visitWrite = new VisitWrite();
        visitWrite.write(AlphaCrawler.getVisitStats());

        UrlWrite urlWrite = new UrlWrite();
        urlWrite.write(AlphaCrawler.getUrlStats());
    }

}