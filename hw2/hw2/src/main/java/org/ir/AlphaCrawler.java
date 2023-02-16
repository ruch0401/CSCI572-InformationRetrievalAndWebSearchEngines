package org.ir;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.ir.model.AlphaCrawlerPojo;

import java.util.Set;
import java.util.regex.Pattern;

public class AlphaCrawler extends WebCrawler {

    final AlphaCrawlerPojo alphaCrawlerPojo = new AlphaCrawlerPojo(Controller.SEED_URL);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|mp3|mp4|zip|gz))$");

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
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();
            links.forEach(link -> {
                logger.info("This is anchor message: " + link.getURL());
            });
        }
    }
}