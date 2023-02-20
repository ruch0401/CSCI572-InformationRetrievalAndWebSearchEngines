package org.ir;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.ir.controller.CrawlerController;
import org.ir.model.AlphaCrawlerPojo;
import org.ir.model.FetchCrawlStat;
import org.ir.model.UrlCrawlStat;
import org.ir.model.VisitCrawlStat;

import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class AlphaCrawler extends WebCrawler {
    final AlphaCrawlerPojo alphaCrawlerPojo = new AlphaCrawlerPojo(CrawlerController.SEED_URL);
    private static List<FetchCrawlStat> fetchCrawlStats;
    private static List<VisitCrawlStat> visitCrawlStats;
    private static List<UrlCrawlStat> urlCrawlStats;
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mp3|mp4|zip|gz|json))$");

    public AlphaCrawler(List<FetchCrawlStat> fetchCrawlStats, List<VisitCrawlStat> visitCrawlStats, List<UrlCrawlStat> urlCrawlStats) {
        AlphaCrawler.fetchCrawlStats = fetchCrawlStats;
        AlphaCrawler.visitCrawlStats = visitCrawlStats;
        AlphaCrawler.urlCrawlStats = urlCrawlStats;
    }

    public static List<FetchCrawlStat> getFetchStats() {
        return fetchCrawlStats;
    }

    public static List<VisitCrawlStat> getVisitStats() {
        return visitCrawlStats;
    }

    public static List<UrlCrawlStat> getUrlStats() {
        return urlCrawlStats;
    }

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "<a href="https://www.latimes.com/">...</a>". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith(alphaCrawlerPojo.getSeedUrl());
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        try {
            String url = page.getWebURL().getURL();
            int statusCode = page.getStatusCode();
            long downloadSize = page.getContentData().length;
            String contentType = page.getContentType();

            int numberOfOutlinks = 0;
            ParseData parseData = page.getParseData();
            if (parseData instanceof HtmlParseData) {
                numberOfOutlinks = parseData.getOutgoingUrls().size();
            }

            System.out.printf("URL: %s | Status Code: %d%n", url, statusCode);

            FetchCrawlStat fetchCrawlStat = new FetchCrawlStat(url, statusCode);
            fetchCrawlStats.add(fetchCrawlStat);

            if (statusCode >= 200 && statusCode <= 299) {
                VisitCrawlStat visitCrawlStat = new VisitCrawlStat(url, downloadSize, numberOfOutlinks, contentType);
                visitCrawlStats.add(visitCrawlStat);
            } else {
                return;
            }

            URL originUrl = new URL(url);
            for (WebURL webURL : parseData.getOutgoingUrls()) {
                URL outlinkUrl = new URL(webURL.getURL());
                String indicator = originUrl.getHost().equals(outlinkUrl.getHost()) ? "OK" : "N_OK";
                UrlCrawlStat urlCrawlStat = new UrlCrawlStat(webURL.getURL(), indicator);
                urlCrawlStats.add(urlCrawlStat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}